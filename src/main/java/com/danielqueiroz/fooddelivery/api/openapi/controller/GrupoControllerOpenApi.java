package com.danielqueiroz.fooddelivery.api.openapi.controller;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.hateoas.CollectionModel;

import com.danielqueiroz.fooddelivery.api.model.GrupoDTO;
import com.danielqueiroz.fooddelivery.api.model.input.GrupoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Grupos")
public interface GrupoControllerOpenApi {

	@ApiOperation("Lista os grupos")
	CollectionModel<GrupoDTO> buscarTodos();

	@ApiOperation("Busca um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da grupo inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	GrupoDTO buscarPorId(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id);

	@ApiOperation("Cadastra um grupo")
	GrupoDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um grupo", 
			required = true)GrupoInput grupoInput);

	@ApiOperation("Atualiza um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	GrupoDTO atualizar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id, 
			@ApiParam(name = "corpo", value = "Representação de um grupo com os novos dados", 
			required = true)GrupoInput grupoInput);

	@ApiOperation("Exclui um grupo por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Grupo não encontrado", response = Problem.class)
	})
	void deletar(@ApiParam(value = "ID de um grupo", example = "1", required = true) Long id);

}