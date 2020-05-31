package com.danielqueiroz.fooddelivery.api.model.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PedidoInput {

	@Valid
	@NotNull
	private RestauranteIdInput restaurante;
	
	@Valid
	@NotNull
	private FormaPagamanetoIdInput formaPagamento;
	
	@Valid
	@NotNull
	private EnderecoInput enderecoEntrega;

	@Valid
	@Size(min = 1)
	@NotNull
	private List<ItemPedidoInput> itens;
	
}
