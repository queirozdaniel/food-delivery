package com.danielqueiroz.fooddelivery.api.openapi.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

import com.danielqueiroz.fooddelivery.api.exceptionhandler.ProblemMessage;
import com.danielqueiroz.fooddelivery.api.model.CidadeDTO;
import com.danielqueiroz.fooddelivery.api.model.input.CidadeInput;
import com.danielqueiroz.fooddelivery.domain.model.Cidade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista todas as cidades")
	CollectionModel<CidadeDTO> buscarTodos();

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da cidade inválido", response = ProblemMessage.class),
		@ApiResponse(code =404, message = "Cidade não encontrada",response = ProblemMessage.class)
	})
	CidadeDTO buscarPorId(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id);

	@ApiOperation("Cria uma nova cidade")
	CidadeDTO salvar(@ApiParam(value = "corpo", example = "Representação de uma nova cidade", required = true) CidadeInput cidadeInput);

	@ApiOperation("Atualiza uma cidade por ID")
	CidadeDTO atualizar(@ApiParam(value = "corpo", example = "Representação do corpo de uma nova cidade", required = true) CidadeInput cidadeInput, @ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id);

	@ApiOperation("Deleta uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = " Cidade não encontrada",response = ProblemMessage.class)
	})
	ResponseEntity<Cidade> deletar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long id);

}