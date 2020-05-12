package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeEmUsoException;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
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

	public Restaurante salvar(Restaurante restaurante) {

		if (!cozinhaRepository.findById(restaurante.getCozinha().getId()).isEmpty()) {
			return restauranteRepository.save(restaurante);
		}

		return null;
	}

	public List<Restaurante> buscarTodos() {
		return restauranteRepository.findAll();
	}

	public void deletar(Long id) {
		try {

			restauranteRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADA, id));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_RESTAURANTE_EM_USO, id));
		}
	}

	public Restaurante buscarPorId(Long id) {
		return restauranteRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_RESTAURANTE_NAO_ENCONTRADA, id)));
	}

	public int totalRestaurantesPorCozinhaId(Long id) {
		return restauranteRepository.countByCozinhaId(id);
	}

}
