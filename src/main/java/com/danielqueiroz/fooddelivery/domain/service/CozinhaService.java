package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeEmUsoException;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Cozinha;
import com.danielqueiroz.fooddelivery.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	private static final String MSG_COZINHA_NAO_ENCONTRADA = "Cozinha com código %d não encontrado";
	private static final String MSG_COZINHA_EM_USO = "Cozinha com código %d não pode ser removido, pois está sendo usado";

	@Autowired
	private CozinhaRepository repository;

	@Transactional
	public Cozinha salvar(Cozinha cozinha) {
		return repository.save(cozinha);
	}

	@Transactional
	public void deletar(Long id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));
		}

	}

	public List<Cozinha> buscarTodos() {
		return repository.findAll();
	}

	public Cozinha buscarPorId(Long id) {
		if (repository.findById(id).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
		}

		return repository.findById(id).get();
	}

	public List<Cozinha> buscarTodasContemNoNome(String nome) {
		return repository.findByNomeContaining(nome);
	}

	public Cozinha buscarPorNome(String nome) {
		try {
			return repository.findByNome(nome).get();

		} catch (EmptyResultDataAccessException ex) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha com nome %s não encontrado", nome));
		}
	}

}
