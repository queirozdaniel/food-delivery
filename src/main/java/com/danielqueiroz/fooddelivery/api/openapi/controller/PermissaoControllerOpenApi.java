package com.danielqueiroz.fooddelivery.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;

import com.danielqueiroz.fooddelivery.api.model.PermissaoDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

	@ApiOperation("Lista as permissões")
    CollectionModel<PermissaoDTO> buscarTodos();
	
}
