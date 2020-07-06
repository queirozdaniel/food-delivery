package com.danielqueiroz.fooddelivery.api.model.input;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class GrupoInput {

	@ApiModelProperty(value = "Gerente",required = true)
	@NotBlank
	private String nome;
	
}
