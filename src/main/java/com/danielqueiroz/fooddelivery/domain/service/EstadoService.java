package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeEmUsoException;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Estado;
import com.danielqueiroz.fooddelivery.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	private static final String MSG_ESTADO_NAO_ENCONTRADA = "Estado com código %d não encontrado";
	private static final String MSG_ESTADO_EM_USO = "Estado com código %d não pode ser removido, pois está sendo usado";
	
	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public void deletar(Long id) {

		try {
			estadoRepository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADA, id));
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
		}

	}

	public Estado buscarPorId(Long id) {
			return estadoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_ESTADO_NAO_ENCONTRADA, id)));

	}

	public List<Estado> buscarTodos() {
		return estadoRepository.findAll();
	}

}
