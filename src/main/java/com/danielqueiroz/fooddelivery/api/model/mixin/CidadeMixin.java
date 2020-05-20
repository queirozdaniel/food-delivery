package com.danielqueiroz.fooddelivery.api.model.mixin;

import com.danielqueiroz.fooddelivery.domain.model.Estado;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public abstract class CidadeMixin {

	@JsonIgnoreProperties(value = "nome", allowGetters = true)
	private Estado estado;
	
}
