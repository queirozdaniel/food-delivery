package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.FormaPagamentoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.FormaPagamentoDTOAssembler;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private FormaPagamentoDTOAssembler formaPagamentoDtoAssembler;
	
	@GetMapping
	public List<FormaPagamentoDTO> listar(@PathVariable Long restauranteId){
		Restaurante restaurantes = restauranteService.buscarPorId(restauranteId);

		return formaPagamentoDtoAssembler.toCollectionModel(restaurantes.getFormasPagamento());
	}
	
	@DeleteMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	}

	@PutMapping("/{formaPagamentoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
		restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
	}
	
}
