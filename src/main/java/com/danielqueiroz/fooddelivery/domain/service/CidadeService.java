package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeEmUsoException;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Cidade;
import com.danielqueiroz.fooddelivery.domain.repository.CidadeRepository;
import com.danielqueiroz.fooddelivery.domain.repository.EstadoRepository;

@Service
public class CidadeService {

	private static final String MSG_CIDADE_NAO_ENCONTRADA = "Cidade com código %d não encontrado";
	private static final String MSG_CIDADE_EM_USO = "Cidade com código %d não pode ser removida, pois está em uso";

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	
	public Cidade salvar(Cidade cidade) {
		// TODO: Implementar o retorno de um BadRequest caso não seja passado o Id do Estado
		if (!estadoRepository.findById(cidade.getEstado().getId()).isEmpty()) {
			return cidadeRepository.save(cidade);
		}

		return null;
	}

	public List<Cidade> buscarTodos() {
		return cidadeRepository.findAll();
	}

	public void deletar(Long id) {
		try {
			cidadeRepository.deleteById(id);
		
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, id));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
		}
	}

	public Cidade buscarPorId(Long id) {
		if (cidadeRepository.findById(id).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_CIDADE_NAO_ENCONTRADA, id));
		}

		return cidadeRepository.findById(id).get();
	}

}
