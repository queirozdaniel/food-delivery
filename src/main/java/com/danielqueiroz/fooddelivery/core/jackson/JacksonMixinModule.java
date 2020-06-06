package com.danielqueiroz.fooddelivery.core.jackson;

import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.model.mixin.CidadeMixin;
import com.danielqueiroz.fooddelivery.api.model.mixin.CozinhaMixin;
import com.danielqueiroz.fooddelivery.api.model.mixin.RestauranteMixin;
import com.danielqueiroz.fooddelivery.api.model.mixin.UsuarioMixin;
import com.danielqueiroz.fooddelivery.domain.model.Cidade;
import com.danielqueiroz.fooddelivery.domain.model.Cozinha;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.model.Usuario;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Component
public class JacksonMixinModule extends SimpleModule {

	private static final long serialVersionUID = 1L;

	public JacksonMixinModule() {
		setMixInAnnotation(Restaurante.class, RestauranteMixin.class);
		setMixInAnnotation(Cozinha.class, CozinhaMixin.class);
		setMixInAnnotation(Cidade.class, CidadeMixin.class);
		setMixInAnnotation(Usuario.class, UsuarioMixin.class);
	}
	
}
