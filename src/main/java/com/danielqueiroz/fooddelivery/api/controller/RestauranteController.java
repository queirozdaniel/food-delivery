package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.RestauranteApenasNomeDTO;
import com.danielqueiroz.fooddelivery.api.model.RestauranteBasicoDTO;
import com.danielqueiroz.fooddelivery.api.model.RestauranteDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.RestauranteApenasNomeDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.RestauranteBasicoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.RestauranteDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.RestauranteInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.input.RestauranteInput;
import com.danielqueiroz.fooddelivery.api.openapi.controller.RestauranteControllerOpenApi;
import com.danielqueiroz.fooddelivery.core.security.CheckSecurity;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.exception.NegocioException;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.service.RestauranteService;

@CrossOrigin
@RestController
@RequestMapping(value = "/restaurantes", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteController implements RestauranteControllerOpenApi {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private RestauranteDTOAssembler restauranteDTOAssembler;
	
	@Autowired
	private RestauranteBasicoDTOAssembler restauranteBasicoDTOAssembler;
	
	@Autowired
	private RestauranteApenasNomeDTOAssembler restauranteApenasNomeDTOAssembler;

	@Autowired
	private RestauranteInputDisassembler restauranteInputDisassembler;

	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<RestauranteBasicoDTO> buscarTodosResumida() {
		return restauranteBasicoDTOAssembler.toCollectionModel(restauranteService.buscarTodos());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping(params = "projecao=apenas-nome")
	public CollectionModel<RestauranteApenasNomeDTO> buscarApenasNome() {
		return restauranteApenasNomeDTOAssembler.toCollectionModel(restauranteService.buscarTodos());
	}
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping("/{id}")
	public RestauranteDTO buscarPorId(@PathVariable Long id) {
		Restaurante restaurante = restauranteService.buscarPorId(id);

		return restauranteDTOAssembler.toModel(restaurante);
	}

	@CheckSecurity.Restaurantes.PodeEditar
	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RestauranteDTO salvar(@RequestBody @Valid RestauranteInput restauranteInput) {
		Restaurante restauranteCriado = restauranteInputDisassembler.toDomainObject(restauranteInput);

		return restauranteDTOAssembler.toModel(restauranteService.salvar(restauranteCriado));
	}

	@CheckSecurity.Restaurantes.PodeEditarEGerenciar
	@Override
	@PutMapping("/{restauranteId}")
	public RestauranteDTO atualizar(@RequestBody @Valid RestauranteInput restauranteInput, @PathVariable Long restauranteId) {

		Restaurante restauranteRetornado = restauranteService.buscarPorId(restauranteId);

		restauranteInputDisassembler.copyToDomainObject(restauranteInput, restauranteRetornado);

		return restauranteDTOAssembler.toModel(restauranteService.salvar(restauranteRetornado));

	}

	@CheckSecurity.Restaurantes.PodeEditar
	@Override
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Restaurante> deletar(@PathVariable Long id) {

		restauranteService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeEditar
	@Override
	@PutMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> ativar(@PathVariable Long id) {
		restauranteService.ativar(id);
		
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeEditar
	@Override
	@DeleteMapping("/{id}/ativo")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> inativar(@PathVariable Long id) {
		restauranteService.inativar(id);
		
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeEditar
	@Override
	@PutMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void ativarMutiplos(@RequestBody List<Long> ids) {
		try {
			restauranteService.ativar(ids);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Restaurantes.PodeEditar
	@Override
	@DeleteMapping("/ativacoes")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void inativarMutiplos(@RequestBody List<Long> ids) {
		try {
			restauranteService.inativar(ids);
		} catch (EntidadeNaoEncontradaException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@CheckSecurity.Restaurantes.PodeEditarEGerenciar
	@Override
	@PutMapping("/{restauranteId}/abertura")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
		restauranteService.abrir(restauranteId);
		
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeEditarEGerenciar
	@Override
	@PutMapping("/{restauranteId}/fechamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
		restauranteService.fechar(restauranteId);
		
		return ResponseEntity.noContent().build();
	}

}
