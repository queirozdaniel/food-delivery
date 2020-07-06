package com.danielqueiroz.fooddelivery.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormaPagamentoInput {

	@ApiModelProperty(example = "Cartão de débito", required = true)
	@NotBlank
	private String descricao;
	
}
