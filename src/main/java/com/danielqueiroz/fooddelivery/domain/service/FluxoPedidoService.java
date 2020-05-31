package com.danielqueiroz.fooddelivery.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danielqueiroz.fooddelivery.domain.model.Pedido;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;
	
	@Transactional
	public void confirmar(Long pedidoId) {
		Pedido pedido = pedidoService.buscarPorId(pedidoId);
		pedido.confirmar();
	}
	
	@Transactional
	public void cancelar(Long pedidoId) {
	    Pedido pedido = pedidoService.buscarPorId(pedidoId);
	    pedido.cancelar();
	}

	@Transactional
	public void entregar(Long pedidoId) {
	    Pedido pedido = pedidoService.buscarPorId(pedidoId);
		pedido.entregar();
	}
	
	
}
