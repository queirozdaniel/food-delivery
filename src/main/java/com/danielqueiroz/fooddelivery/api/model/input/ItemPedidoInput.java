package com.danielqueiroz.fooddelivery.api.model.input;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPedidoInput {

	@ApiModelProperty(example = "1", required = true)
	@NotNull
	private Long produtoId;
	
	@ApiModelProperty(example = "3", required = true)
	@NotNull
	@PositiveOrZero
	private Integer quantidade;

	@ApiModelProperty(example = "Sem tomates, por favor", required = true)
	private String observacao;
}
