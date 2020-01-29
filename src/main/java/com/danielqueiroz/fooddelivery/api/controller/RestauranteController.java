package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@GetMapping
	public List<Restaurante> buscarTodos() {
		return restauranteService.buscarTodos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(restauranteService.buscarPorId(id));

		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante salvar(@RequestBody Restaurante restaurante) {
		return restauranteService.salvar(restaurante);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Restaurante> atualizar(@RequestBody Restaurante restaurante, @PathVariable Long id) {

		try {
			Restaurante restauranteRetornado = restauranteService.buscarPorId(id);

			BeanUtils.copyProperties(restaurante, restauranteRetornado, "id");
			restauranteService.salvar(restauranteRetornado);

			return ResponseEntity.ok(restauranteRetornado);
		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();

		}

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Restaurante> deletar(@PathVariable Long id) {

		try {
			restauranteService.delete(id);
			return ResponseEntity.noContent().build();
	
		} catch (DataIntegrityViolationException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		
		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}

}
