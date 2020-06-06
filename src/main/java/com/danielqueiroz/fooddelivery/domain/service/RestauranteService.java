package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeEmUsoException;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.FormaPagamento;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.model.Usuario;
import com.danielqueiroz.fooddelivery.domain.repository.CidadeRepository;
import com.danielqueiroz.fooddelivery.domain.repository.CozinhaRepository;
import com.danielqueiroz.fooddelivery.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

	private static final String MSG_RESTAURANTE_NAO_ENCONTRADA = "Restaurante com código %d não encontrado";
	private static final String MSG_RESTAURANTE_EM_USO = "Restaurante com código %d não pode ser removido, pois está sendo usado";

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private FormaPagamentoService formaPagamentoService;
	
	@Transactional
	public Restaurante salvar(Restaurante restaurante) {
		Long idCozinha = restaurante.getCozinha().getId();
		Long idCidade = restaurante.getEndereco().getCidade().getId();
		
		if (cozinhaRepository.findById(idCozinha).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cozinha com id %d", idCidade));
		}
		
		if (cidadeRepository.findById(idCidade).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Não existe cadastro de cidade com id %d", idCidade));
		}
		
		return restauranteRepository.save(restaurante);
	}

	public List<Restaurante> buscarTodos() {
		return restauranteRepository.findAll();
	}

	@Transactional
	public void deletar(Long id) {
		try {
			restauranteRepository.deleteById(id);
			restauranteRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADA, id));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));
		}
	}

	@Transactional
	public void ativar(Long id) {
		Restaurante restaurante = restauranteRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADA, id)));
		
		restaurante.setAtivo(true);
	}
	
	@Transactional
	public void ativar(List<Long> restaurantesId) {
		restaurantesId.forEach(this::ativar);
	}
	
	@Transactional
	public void inativar(Long id) {
		Restaurante restaurante = restauranteRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADA, id)));
		
		restaurante.setAtivo(false);
	}
	
	@Transactional
	public void inativar(List<Long> restaurantesId) {
		restaurantesId.forEach(this::inativar);
	}
	
	@Transactional
	public void desassociarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarPorId(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(formaPagamentoId);
		
		restaurante.getFormasPagamento().remove(formaPagamento);
	}
	
	@Transactional
	public void associarFormaPagamento(Long restauranteId, Long formaPagamentoId) {
		Restaurante restaurante = buscarPorId(restauranteId);
		FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(formaPagamentoId);
		
		restaurante.getFormasPagamento().add(formaPagamento);
	}
	
	@Transactional
	public void abrir(Long restauranteId) {
	    Restaurante restauranteAtual = buscarPorId(restauranteId);
	    
	    restauranteAtual.abrir();
	}

	@Transactional
	public void fechar(Long restauranteId) {
	    Restaurante restauranteAtual = buscarPorId(restauranteId);
	    
	    restauranteAtual.fechar();
	}   
	
	@Transactional
	public void desassociarResponsavel(Long restauranteId, Long usuarioId) {
	    Restaurante restaurante = buscarPorId(restauranteId);
	    Usuario usuario = usuarioService.buscarPorId(usuarioId);
	    
	    restaurante.removerResponsavel(usuario);
	}

	@Transactional
	public void associarResponsavel(Long restauranteId, Long usuarioId) {
	    Restaurante restaurante = buscarPorId(restauranteId);
	    Usuario usuario = usuarioService.buscarPorId(usuarioId);
	    
	    restaurante.adicionarResponsavel(usuario);
	}
	
	public Restaurante buscarPorId(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADA, id)));
	}

	public int totalRestaurantesPorCozinhaId(Long id) {
		return restauranteRepository.countByCozinhaId(id);
	}

}
