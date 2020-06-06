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
	public void confirmar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarPorCodigo(codigoPedido);
		pedido.confirmar();
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
	    Pedido pedido = pedidoService.buscarPorCodigo(codigoPedido);
	    pedido.cancelar();
	}

	@Transactional
	public void entregar(String codigoPedido) {
	    Pedido pedido = pedidoService.buscarPorCodigo(codigoPedido);
		pedido.entregar();
	}
	
	
}
