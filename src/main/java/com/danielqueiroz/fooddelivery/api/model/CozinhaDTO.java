package com.danielqueiroz.fooddelivery.api.model;

import com.danielqueiroz.fooddelivery.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Cozinha", description = "Representação de uma Cozinha")
@Getter
@Setter
public class CozinhaDTO {
	
	@ApiModelProperty(example = "1")
	@JsonView(RestauranteView.Resumida.class)
	private Long id;
	
	@ApiModelProperty(example = "Brasileira")
	@JsonView(RestauranteView.Resumida.class)
	private String nome;
	
}
