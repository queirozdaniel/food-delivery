package com.danielqueiroz.fooddelivery.api.model;

import com.danielqueiroz.fooddelivery.api.model.view.RestauranteView;
import com.fasterxml.jackson.annotation.JsonView;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CozinhaDTO {
	
	@JsonView(RestauranteView.Resumida.class)
	private Long id;
	
	@JsonView(RestauranteView.Resumida.class)
	private String nome;
	
}
