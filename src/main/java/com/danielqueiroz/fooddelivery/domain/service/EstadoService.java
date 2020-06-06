package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeEmUsoException;
import com.danielqueiroz.fooddelivery.domain.exception.EstadoNaoEncontradoException;
import com.danielqueiroz.fooddelivery.domain.model.Estado;
import com.danielqueiroz.fooddelivery.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	private static final String MSG_ESTADO_EM_USO = "Estado com código %d não pode ser removido, pois está sendo usado";
	
	@Autowired
	private EstadoRepository estadoRepository;

	@Transactional
	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	@Transactional
	public void deletar(Long id) {

		try {
			estadoRepository.deleteById(id);
			estadoRepository.flush();
		} catch (EmptyResultDataAccessException ex) {
			throw new EstadoNaoEncontradoException(id);
		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
		}

	}

	public Estado buscarPorId(Long id) {
			return estadoRepository.findById(id).orElseThrow(() -> new EstadoNaoEncontradoException(id));
	}

	public List<Estado> buscarTodos() {
		return estadoRepository.findAll();
	}

}
