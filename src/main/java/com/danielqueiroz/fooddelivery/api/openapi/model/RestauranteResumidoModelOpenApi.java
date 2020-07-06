package com.danielqueiroz.fooddelivery.api.openapi.model;

import java.math.BigDecimal;

import com.danielqueiroz.fooddelivery.api.model.CozinhaDTO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel("RestauranteResumidoModel")
@Getter
@Setter
public class RestauranteResumidoModelOpenApi {

	@ApiModelProperty(example = "1")
	private Long id;	
	@ApiModelProperty(example = "Bolos e Doces")
	private String nome;
	@ApiModelProperty(example = "5.00")
	private BigDecimal taxaFrete;
	
	private CozinhaDTO cozinha;
	
}
