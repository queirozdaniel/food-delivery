package com.danielqueiroz.fooddelivery.api.model.input;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

	@Valid
	@NotNull
	private ProdutoIdInput produtoId;
	
	@NotNull
	@PositiveOrZero
	private Integer quantidade;

	private String observacao;
}
