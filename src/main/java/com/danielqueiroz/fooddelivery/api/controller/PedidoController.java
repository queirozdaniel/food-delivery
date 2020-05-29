package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.PedidoDTO;
import com.danielqueiroz.fooddelivery.api.model.PedidoResumoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.PedidoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.PedidoResumoDTOAssembler;
import com.danielqueiroz.fooddelivery.domain.model.Pedido;
import com.danielqueiroz.fooddelivery.domain.repository.PedidoRepository;
import com.danielqueiroz.fooddelivery.domain.service.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private PedidoService pedidoService;
	
	@Autowired 
	private PedidoDTOAssembler pedidoDtoAssembler;

	@Autowired 
	private PedidoResumoDTOAssembler pedidoResumoDtoAssembler;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@GetMapping
	public List<PedidoResumoDTO> listar(){
		List<Pedido> pedidos = pedidoRepository.findAll();
		return pedidoResumoDtoAssembler.toCollectionModel(pedidos);
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoDTO buscarPedido(@PathVariable Long pedidoId) {
		Pedido pedido = pedidoService.buscarPorId(pedidoId);
		
		return pedidoDtoAssembler.toModel(pedido);
	}
	
	
}
