package com.danielqueiroz.fooddelivery.api.openapi.controller;

import java.util.List;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

import com.danielqueiroz.fooddelivery.api.model.CozinhaDTO;
import com.danielqueiroz.fooddelivery.api.model.input.CozinhaInput;
import com.danielqueiroz.fooddelivery.domain.model.Cozinha;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista as cozinhas com paginação")
	PagedModel<CozinhaDTO> listarTodas(Pageable pageable);

	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaDTO buscarPorId(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id);

	@ApiOperation("Cadastra uma cozinha nova")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaDTO adicionar(@ApiParam(value = "ID de uma cozinha", example = "Representação do corpo de uma Cozinha", required = true) CozinhaInput cozinhaInput);

	@ApiOperation("Atualiza uma cozinha já existente")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	CozinhaDTO atualizar(@ApiParam(value = "corpo", example = "Representação do corpo de de uma cozinha com dados novos", required = true) CozinhaInput cozinhaInput, 
					@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id);

	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	ResponseEntity<Cozinha> deletar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long id);

	
	@ApiOperation("Busca uma cozinha contendo palavra em nome")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	ResponseEntity<?> pesquisaEmNome(@ApiParam(value = "string", example = "'Burger'", required = true) String nome);

	@ApiOperation("Busca todas cozinhas contendo palavra em nome")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)
	})
	List<Cozinha> listarTodasQueContemEmNome(@ApiParam(value = "string", example = "'Burger'", required = true) String nome);

}