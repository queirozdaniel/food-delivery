package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeEmUsoException;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Estado;
import com.danielqueiroz.fooddelivery.domain.repository.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;

	public Estado salvar(Estado estado) {
		return estadoRepository.save(estado);
	}

	public void excluir(Long id) {
		Optional<Estado> estado = estadoRepository.findById(id);
		try {
			if (estado.isPresent()) {
				estadoRepository.deleteById(id);

			}
			throw new EntidadeNaoEncontradaException(String.format("Não existe estado com código %d", id));
		} catch (DataIntegrityViolationException ex) {
			throw new EntidadeEmUsoException(
					String.format("Estado de códifo %d não pode ser removida pois está em uso", id));
		}

	}

	public void delete(Long id) {
		if (estadoRepository.findById(id).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Estado com código %d não encontrado", id));
		}
		estadoRepository.deleteById(id);
	}

	public Estado buscarPorId(Long id) {
		if (estadoRepository.findById(id).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Estado com código %d não encontrado", id));
		}

		return estadoRepository.findById(id).get();
	}

	public List<Estado> buscarTodos() {
		return estadoRepository.findAll();
	}

}
