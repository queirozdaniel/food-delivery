package com.danielqueiroz.fooddelivery.api.openapi.controller;

import java.util.List;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ResponseEntity;

import com.danielqueiroz.fooddelivery.api.model.EstadoDTO;
import com.danielqueiroz.fooddelivery.api.model.input.EstadoInput;
import com.danielqueiroz.fooddelivery.domain.model.Estado;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista todos estados")
	List<EstadoDTO> buscarTodos();

	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	EstadoDTO buscarPorId(@ApiParam(value = "ID de um estado", example = "1", required = true) Long id);

	@ApiOperation("Busca um estado por ID")
	EstadoDTO salvar(@ApiParam(value = "corpo", example = "Representação de um Estado", required = true) EstadoInput estadoInput);

	@ApiOperation("Atualiza os dados de um estado já existente")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	EstadoDTO atualizar(@ApiParam(value = "corpo", example = "Representação de um Estado com novos dados", required = true) EstadoInput estadoInput, 
			@ApiParam(value = "ID de um estado", example = "1", required = true) Long id);

	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)
	})
	ResponseEntity<Estado> deletar(@ApiParam(value = "ID de um estado", example = "1", required = true) Long id);

}