package com.danielqueiroz.fooddelivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.FormaPagamentoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.FormaPagamentoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.openapi.controller.RestauranteFormaPagamentoControllerOpenApi;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.CheckSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.service.RestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteFormaPagamentoController implements RestauranteFormaPagamentoControllerOpenApi {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDtoAssembler;
	
	@Autowired
	private CreateLinks createLinks;
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<FormaPagamentoDTO> buscarTodos(@PathVariable Long restauranteId){
		Restaurante restaurantes = restauranteService.buscarPorId(restauranteId);

		CollectionModel<FormaPagamentoDTO> formasPagamentoDto = formaPagamentoDtoAssembler.toCollectionModel(restaurantes.getFormasPagamento())
				.removeLinks()
	            .add(createLinks.linkToRestauranteFormasPagamento(restauranteId))
	            .add(createLinks.linkToRestauranteFormaPagamentoAssociacao(restauranteId, "associar"));
		
		formasPagamentoDto.getContent().forEach(formaPagamento -> {
			formaPagamento.add(createLinks
					.linkToRestauranteFormaPagamentoDesassociacao(restauranteId, formaPagamento.getId(), "desassociar"));
		});
		
		return formasPagamentoDto;
	}
	
	@CheckSecurity.Restaurantes.PodeEditarEGerenciar
	@Override
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.Restaurantes.PodeEditarEGerenciar
	@Override
	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	
		return ResponseEntity.noContent().build();
	}
	
}
