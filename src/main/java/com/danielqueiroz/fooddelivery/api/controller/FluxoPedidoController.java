package com.danielqueiroz.fooddelivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.domain.service.FluxoPedidoService;

@RestController
@RequestMapping("/pedidos/{pedidoId}")
public class FluxoPedidoController {

	@Autowired
	private FluxoPedidoService fluxoPedidoService;
	
	@PutMapping("/confirmar")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void confirmar(@PathVariable Long pedidoId) {
		fluxoPedidoService.confirmar(pedidoId);
	}
	
	@PutMapping("/cancelamento")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void cancelar(@PathVariable Long pedidoId) {
		fluxoPedidoService.cancelar(pedidoId);
	}

	@PutMapping("/entrega")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void entregar(@PathVariable Long pedidoId) {
		fluxoPedidoService.entregar(pedidoId);
	}
	
	
}
