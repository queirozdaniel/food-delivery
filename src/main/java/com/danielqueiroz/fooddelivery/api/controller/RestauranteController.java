package com.danielqueiroz.fooddelivery.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
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

import com.danielqueiroz.fooddelivery.core.validation.ValidacaoException;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.service.RestauranteService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private SmartValidator smartValidator;

	@GetMapping
	public List<Restaurante> buscarTodos() {
		return restauranteService.buscarTodos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Restaurante> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(restauranteService.buscarPorId(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Restaurante salvar(@RequestBody @Valid Restaurante restaurante) {
		return restauranteService.salvar(restaurante);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Restaurante> atualizar(@RequestBody @Valid Restaurante restaurante, @PathVariable Long id) {

		Restaurante restauranteRetornado = restauranteService.buscarPorId(id);

		BeanUtils.copyProperties(restaurante, restauranteRetornado, "id", "formasPagamento", "endereco",
				"dataCadastro");
		restauranteService.salvar(restauranteRetornado);

		return ResponseEntity.ok(restauranteRetornado);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Restaurante> deletar(@PathVariable Long id) {

		restauranteService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@PatchMapping("/{id}")
	public ResponseEntity<?> atualizarParcial(@RequestBody Map<String, Object> campos, @PathVariable Long id,
			HttpServletRequest request) {

		Restaurante restauranteAtual = restauranteService.buscarPorId(id);
		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}

		modificaCampos(campos, restauranteAtual, request);
		validate(restauranteAtual, "restaurante");

		return atualizar(restauranteAtual, id);
	}

	private void validate(Restaurante restaurante, String objectName) {
		BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(restaurante, objectName);

		smartValidator.validate(restaurante, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new ValidacaoException(bindingResult);
		}

	}

	private void modificaCampos(Map<String, Object> campos, Restaurante restauranteAtual, HttpServletRequest request) {

		ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

		// TODO: Criar um Bean para fazer o acesso aos valores
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true);

			Restaurante restauranteOrigem = objectMapper.convertValue(campos, Restaurante.class);

			campos.forEach((key, value) -> {
				Field field = ReflectionUtils.findField(Restaurante.class, key);
				field.setAccessible(true);

				Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

				ReflectionUtils.setField(field, restauranteAtual, novoValor);

			});

		} catch (IllegalArgumentException e) {
			Throwable rootCause = e.getCause();
			throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
		}
	}

}
