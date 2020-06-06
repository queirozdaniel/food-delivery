package com.danielqueiroz.fooddelivery.api.model;

import java.math.BigDecimal;

import com.danielqueiroz.fooddelivery.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RestauranteDTO {

	@JsonView({RestauranteView.Resumida.class, RestauranteView.ApenasNome.class })
	private Long id;
	
	@JsonView({RestauranteView.Resumida.class, RestauranteView.ApenasNome.class })
	private String nome;

	@JsonView(RestauranteView.Resumida.class)
	private BigDecimal taxaFrete;
	
	@JsonView(RestauranteView.Resumida.class)
	private CozinhaDTO cozinha;

	private Boolean ativo;
	private Boolean aberto;
	private EnderecoDTO endereco;
}
