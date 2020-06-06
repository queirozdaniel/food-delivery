package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.danielqueiroz.fooddelivery.api.model.RestauranteDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.RestauranteDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.RestauranteInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.input.RestauranteInput;
import com.danielqueiroz.fooddelivery.api.model.view.RestauranteView;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.exception.NegocioException;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.service.RestauranteService;
import com.fasterxml.jackson.annotation.JsonView;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;

	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@JsonView(RestauranteView.Resumida.class)
	@GetMapping
	public List<RestauranteDTO> buscarTodosResumida() {
		return restauranteDTOAssembler.toCollectionModel(restauranteService.buscarTodos());
	}
	
	@JsonView(RestauranteView.ApenasNome.class)
	@GetMapping(params = "projecao=apenas-nome")
	public List<RestauranteDTO> buscarApenasNome() {
		return restauranteDTOAssembler.toCollectionModel(restauranteService.buscarTodos());
	}
	
//	@GetMapping
//	public MappingJacksonValue buscarTodos(@RequestParam(required = false) String projecao) {
//		List<Restaurante> resturantes = restauranteService.buscarTodos();
//		List<RestauranteDTO> restautantesDTO = restauranteDTOAssembler.toCollectionModel(resturantes);
//
//		MappingJacksonValue restauranteMapper = new MappingJacksonValue(restautantesDTO);
//
//		restauranteMapper.setSerializationView(RestauranteView.Resumida.class);
//
//		if ("apenas-nome".equals(projecao)) {
//			restauranteMapper.setSerializationView(RestauranteView.ApenasNome.class);
//		} else if ("resumo".equals(projecao)) {
//			restauranteMapper.setSerializationView(null);
//		}
//
//		return restauranteMapper;
//	}

	@GetMapping("/{id}")
	public RestauranteDTO buscarPorId(@PathVariable Long id) {
		Restaurante restaurante = restauranteService.buscarPorId(id);

		return restauranteDTOAssembler.toModel(restaurante);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restauranteCriado = restauranteInputDisassembler.toDomainObject(restauranteInput);

		return restauranteDTOAssembler.toModel(restauranteService.salvar(restauranteCriado));
	}

	@PutMapping("/{id}")
	public RestauranteDTO atualizar(@RequestBody @Valid RestauranteInput restauranteInput, @PathVariable Long id) {

		Restaurante restauranteRetornado = restauranteService.buscarPorId(id);

		restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteRetornado);

		return restauranteDTOAssembler.toModel(restauranteService.salvar(restauranteRetornado));

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Restaurante> deletar(@PathVariable Long id) {

		restauranteService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativar(@PathVariable Long id) {
		restauranteService.ativar(id);
	}

	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativar(@PathVariable Long id) {
		restauranteService.inativar(id);
	}

	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMutiplos(@RequestBody List<Long> ids) {
		try {
			restauranteService.ativar(ids);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMutiplos(@RequestBody List<Long> ids) {
		try {
			restauranteService.inativar(ids);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void abrir(@PathVariable Long restauranteId) {
		restauranteService.abrir(restauranteId);
	}

	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void fechar(@PathVariable Long restauranteId) {
		restauranteService.fechar(restauranteId);
	}

}
