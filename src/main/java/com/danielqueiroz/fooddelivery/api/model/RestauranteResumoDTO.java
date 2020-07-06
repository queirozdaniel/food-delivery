package com.danielqueiroz.fooddelivery.api.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "RestauranteResumo", description = "Representação de um RestauranteResumo")
@Setter
@Getter
public class RestauranteResumoDTO {

	@ApiModelProperty(example = "2")
	private Long id;
	
	@ApiModelProperty(example = "Pizzarione")
	private String nome;
	
}
