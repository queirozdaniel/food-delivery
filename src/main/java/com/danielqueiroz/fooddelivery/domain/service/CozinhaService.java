package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeEmUsoException;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Cozinha;
import com.danielqueiroz.fooddelivery.domain.repository.CozinhaRepository;

@Service
public class CozinhaService {

	@Autowired
	private CozinhaRepository repository;

	public Cozinha salvar(Cozinha cozinha) {
		return repository.save(cozinha);
	}

	public void excluir(Long id) {
		Optional<Cozinha> cozinha = repository.findById(id);
		try {
			if (cozinha.isPresent()) {
				repository.deleteById(id);

			}
			throw new EntidadeNaoEncontradaException(String.format("Não existe cozinha com código", id));
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format("Cozinha de códifo %d não pode ser removida pois está em uso", id));
		}

	}

	public void deletar(Long id) {
		if (repository.findById(id).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha com código %d não encontrado", id));
		}
		repository.deleteById(id);
	}
	
	public List<Cozinha> buscarTodos(){
		return repository.findAll();
	}
	
	public Cozinha buscarPorId(Long id) {
		if (repository.findById(id).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Cozinha com código %d não encontrado", id));
		}
		
		return repository.findById(id).get();
	}
	
	
	public List<Cozinha> buscarTodasContemNoNome(String nome) {
		return repository.findByNomeContaining(nome);
	}

	public Cozinha buscarPorNome(String nome) {
		Optional<Cozinha> cozinha = repository.findByNome(nome);
		if (cozinha.isPresent()) {
			return cozinha.get();
		}
		throw new EntidadeNaoEncontradaException(String.format("Cozinha com nome %s não encontrado", nome));
	}

}
