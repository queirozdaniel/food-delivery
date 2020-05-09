package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Cidade;
import com.danielqueiroz.fooddelivery.domain.repository.CidadeRepository;
import com.danielqueiroz.fooddelivery.domain.repository.EstadoRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	
	public Cidade salvar(Cidade cidade) {

		if (!estadoRepository.findById(cidade.getEstado().getId()).isEmpty()) {
			return cidadeRepository.save(cidade);
		}

		return null;
	}

	public List<Cidade> buscarTodos() {
		return cidadeRepository.findAll();
	}

	public void delete(Long id) {
		if (cidadeRepository.findById(id).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade com código %d não encontrado", id));
		}
		cidadeRepository.deleteById(id);
	}

	public Cidade buscarPorId(Long id) {
		if (cidadeRepository.findById(id).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Cidade com código %d não encontrado", id));
		}

		return cidadeRepository.findById(id).get();
	}

}
