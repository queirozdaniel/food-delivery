package com.danielqueiroz.fooddelivery.domain.event;

import com.danielqueiroz.fooddelivery.domain.model.Pedido;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {

	private Pedido pedido;
	
}
