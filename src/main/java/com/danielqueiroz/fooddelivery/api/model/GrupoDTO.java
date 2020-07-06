package com.danielqueiroz.fooddelivery.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Grupo", description = "Representação de um Grupo")
@Getter
@Setter
public class GrupoDTO {

	@ApiModelProperty(example = "1")
	private Long id;
	
	@ApiModelProperty(example = "Gerente")
	private String nome;
	
}
