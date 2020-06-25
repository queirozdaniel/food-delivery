package com.danielqueiroz.fooddelivery.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danielqueiroz.fooddelivery.domain.model.Pedido;
import com.danielqueiroz.fooddelivery.domain.repository.PedidoRepository;

@Service
public class FluxoPedidoService {

	@Autowired
	private PedidoService pedidoService;

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Transactional
	public void confirmar(String codigoPedido) {
		Pedido pedido = pedidoService.buscarPorCodigo(codigoPedido);
		pedido.confirmar();
		
		pedidoRepository.save(pedido);
	}
	
	@Transactional
	public void cancelar(String codigoPedido) {
	    Pedido pedido = pedidoService.buscarPorCodigo(codigoPedido);
	    pedido.cancelar();
	    
	    pedidoRepository.save(pedido);
	}

	@Transactional
	public void entregar(String codigoPedido) {
	    Pedido pedido = pedidoService.buscarPorCodigo(codigoPedido);
		pedido.entregar();
	}
	
	
}
