package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.repository.CozinhaRepository;
import com.danielqueiroz.fooddelivery.domain.repository.RestauranteRepository;

@Service
public class RestauranteService {

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
	
	public List<Restaurante> buscarTodos(){
		return restauranteRepository.findAll();
	}
	
	public void delete(Long id) {
		if (restauranteRepository.findById(id).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Restaurante com código %d não encontrado", id));
		}
		restauranteRepository.deleteById(id);
	}
	
	public Restaurante buscarPorId(Long id) {
		if (restauranteRepository.findById(id).isEmpty()) {
			throw new EntidadeNaoEncontradaException(String.format("Restaurante com código %d não encontrado", id));
		}
		
		return restauranteRepository.findById(id).get();
	}
	
	public int totalRestaurantesPorCozinhaId(Long id) {
		return restauranteRepository.countByCozinhaId(id);
	}
	
	
}
