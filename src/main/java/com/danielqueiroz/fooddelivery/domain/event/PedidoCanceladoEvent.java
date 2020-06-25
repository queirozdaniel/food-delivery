package com.danielqueiroz.fooddelivery.domain.event;

import com.danielqueiroz.fooddelivery.domain.model.Pedido;

import lombok.Getter;

import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class PedidoCanceladoEvent {
	
	private Pedido pedido;
	
}
