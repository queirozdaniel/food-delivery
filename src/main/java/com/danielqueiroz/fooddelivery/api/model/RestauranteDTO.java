package com.danielqueiroz.fooddelivery.api.model;

import java.math.BigDecimal;

import com.danielqueiroz.fooddelivery.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel(value = "Restaurante", description = "Representação de um Restaurante")
@Getter
@Setter
public class RestauranteDTO {

	@ApiModelProperty(example = "2")
	@JsonView({RestauranteView.Resumida.class, RestauranteView.ApenasNome.class })
	private Long id;
	
	@ApiModelProperty(example = "Pizzarione")
	@JsonView({RestauranteView.Resumida.class, RestauranteView.ApenasNome.class })
	private String nome;

	@ApiModelProperty(example = "6.00")
	@JsonView(RestauranteView.Resumida.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.Resumida.class)
	private CozinhaDTO cozinha;

	private Boolean ativo;
	private Boolean aberto;
	private EnderecoDTO endereco;
}
