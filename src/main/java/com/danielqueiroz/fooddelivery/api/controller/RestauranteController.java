package com.danielqueiroz.fooddelivery.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.repository.RestauranteRepository;
import com.danielqueiroz.fooddelivery.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;
	
	@Autowired 
	private RestauranteRepository restauranteRepository;

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

	@GetMapping("/total")
	public int totalPorCozinhaId(Long id) {
		return restauranteService.totalRestaurantesPorCozinhaId(id);
	}
	
	@GetMapping("/porNome")
	public List<Restaurante> buscarTodosPorNomeECozinha(String nome, Long id) {
		return restauranteRepository.consultaPorNomeECozinha(nome, id);
	}
	
	@GetMapping("/porNomeTaxa")
	public List<Restaurante> buscaTodosPorNomeEFrete(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
		return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
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
	
	
	
	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@RequestBody Map<String, Object> campos, @PathVariable Long id) {
		
		Restaurante restauranteAtual = restauranteService.buscarPorId(id);
		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		modificaCampos(campos, restauranteAtual);
		
		return atualizar(restauranteAtual, id);
	}

	private void modificaCampos(Map<String, Object> campos, Restaurante restauranteAtual) {
		// TODO: Criar um Bean para fazer o acesso aos valores
		Restaurante restauranteOrigem = new ObjectMapper().convertValue(campos, Restaurante.class);
		
		campos.forEach((key,value) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, key);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
			ReflectionUtils.setField(field, restauranteAtual, novoValor);
			
		});
	}
	

}
