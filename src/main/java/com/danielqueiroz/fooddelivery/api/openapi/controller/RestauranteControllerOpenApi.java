package com.danielqueiroz.fooddelivery.api.openapi.controller;

import java.util.List;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ResponseEntity;

import com.danielqueiroz.fooddelivery.api.model.RestauranteDTO;
import com.danielqueiroz.fooddelivery.api.model.input.RestauranteInput;
import com.danielqueiroz.fooddelivery.api.model.view.RestauranteView;
import com.danielqueiroz.fooddelivery.api.openapi.model.RestauranteResumidoModelOpenApi;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	@ApiOperation(value = "Lista restaurantes", response = RestauranteResumidoModelOpenApi.class)
	@ApiImplicitParams({
		@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome",
				name = "projecao", paramType = "query", type = "string")
	})
	@JsonView(RestauranteView.Resumida.class)
	List<RestauranteDTO> buscarTodosResumida();

	@ApiOperation(value = "Lista restaurantes", hidden = true)
	List<RestauranteDTO> buscarApenasNome();

	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteDTO buscarPorId(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

	@ApiOperation("Cadastra um restaurante")
	RestauranteDTO salvar(RestauranteInput restauranteInput);

	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	RestauranteDTO atualizar(RestauranteInput restauranteInput,
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

	@ApiOperation("Excluir um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	ResponseEntity<Restaurante> deletar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

	
	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

	@ApiOperation("Inativa um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long id);

	@ApiOperation("Ativa múltiplos restaurantes")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")
	})
	void ativarMutiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> ids);

	@ApiOperation("Inativa múltiplos restaurantes")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurantes inativados com sucesso")
	})
	void inativarMutiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> ids);

	@ApiOperation("Abre um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({
		@ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
		@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)
	})
	void fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

}