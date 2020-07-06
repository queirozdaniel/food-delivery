package com.danielqueiroz.fooddelivery.api.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.danielqueiroz.fooddelivery.api.model.FotoProdutoDTO;
import com.danielqueiroz.fooddelivery.api.model.input.FotoProdutoInput;

public interface RestauranteProdutoFotoControllerOpenApi {

	FotoProdutoDTO atualizarFoto(Long restauranteId, Long produtoId, FotoProdutoInput fotoProduto) throws IOException;

	FotoProdutoDTO buscar(Long restauranteId, Long produtoId);

	ResponseEntity<?> buscarFoto(Long restauranteId, Long produtoId, String acceptHeader)
			throws HttpMediaTypeNotAcceptableException;

	void excluir(Long restauranteId, Long produtoId);

}