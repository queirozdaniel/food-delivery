package com.danielqueiroz.fooddelivery.api.openapi.controller;

import java.util.List;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.ServletWebRequest;

import com.danielqueiroz.fooddelivery.api.model.FormaPagamentoDTO;
import com.danielqueiroz.fooddelivery.api.model.input.FormaPagamentoInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation("Lista as formas de pagamento")
	ResponseEntity<List<FormaPagamentoDTO>> listar(ServletWebRequest request);

	@ApiOperation("Busca uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	ResponseEntity<FormaPagamentoDTO> buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id, ServletWebRequest request);

	@ApiOperation("Cadastra uma forma de pagamento")
	FormaPagamentoDTO adicionar(@ApiParam(name = "corpo", value = "Representação de uma Forma de Pagamento", 
			required = true) FormaPagamentoInput formaPagamentoInput);

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	FormaPagamentoDTO atualizar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long id, 
			@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", 
			required = true) FormaPagamentoInput formaPagamentoInput);

	@ApiOperation("Exclui uma forma de pagamento por ID")
	@ApiResponses({
		@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)
	})
	void remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

}