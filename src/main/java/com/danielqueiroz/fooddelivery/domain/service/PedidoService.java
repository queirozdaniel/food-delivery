package com.danielqueiroz.fooddelivery.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielqueiroz.fooddelivery.domain.exception.PedidoNaoEncontradoException;
import com.danielqueiroz.fooddelivery.domain.model.Pedido;
import com.danielqueiroz.fooddelivery.domain.repository.PedidoRepository;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	public Pedido buscarPorId(Long id) {
		return pedidoRepository.findById(id).orElseThrow( () -> new PedidoNaoEncontradoException(id));
	}
	
}
