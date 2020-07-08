package com.danielqueiroz.fooddelivery.api.openapi.controller;

import org.springframework.beans.factory.parsing.Problem;
import org.springframework.hateoas.CollectionModel;

import com.danielqueiroz.fooddelivery.api.model.UsuarioDTO;
import com.danielqueiroz.fooddelivery.api.model.input.SenhaInput;
import com.danielqueiroz.fooddelivery.api.model.input.UsuarioComSenhaInput;
import com.danielqueiroz.fooddelivery.api.model.input.UsuarioInput;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Lista todos os usuários")
	CollectionModel<UsuarioDTO> listar();

	@ApiOperation("Busca um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
	UsuarioDTO buscar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id);

	@ApiOperation("Cadastra um usuário")
    @ApiResponses({
        @ApiResponse(code = 201, message = "Usuário cadastrado"),
    })
	UsuarioDTO adicionar(@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true) UsuarioComSenhaInput usuarioInput);

	@ApiOperation("Atualiza um usuário por ID")
    @ApiResponses({
        @ApiResponse(code = 200, message = "Usuário atualizado"),
        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
    })
	UsuarioDTO atualizar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id, 
			@ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados",
            required = true) UsuarioInput usuarioInput);

	 @ApiOperation("Atualiza a senha de um usuário")
	    @ApiResponses({
	        @ApiResponse(code = 204, message = "Senha alterada com sucesso"),
	        @ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)
	    })
	void alterarSenha(@ApiParam(value = "ID do usuário", example = "1", required = true) Long id, 
			 @ApiParam(name = "corpo", value = "Representação de uma nova senha", 
             required = true) SenhaInput senha);

}