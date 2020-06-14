package com.danielqueiroz.fooddelivery.api.controller;

import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.input.FotoProdutoInput;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public void atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,@Valid FotoProdutoInput fotoProduto) {
		
		var nomeArquivo = UUID.randomUUID().toString() + "_" + fotoProduto.getArquivo().getOriginalFilename();
		var arquivoFoto = Path.of("/Users/danie/Documents", nomeArquivo);

		System.out.println(arquivoFoto);
		System.out.println(fotoProduto.getArquivo().getContentType());
		System.out.println(fotoProduto.getDescricao());
		
		try {
			fotoProduto.getArquivo().transferTo(arquivoFoto);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
}
