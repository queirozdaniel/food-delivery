package com.danielqueiroz.fooddelivery.api.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.danielqueiroz.fooddelivery.api.model.FotoProdutoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.FotoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.input.FotoProdutoInput;
import com.danielqueiroz.fooddelivery.domain.model.FotoProduto;
import com.danielqueiroz.fooddelivery.domain.model.Produto;
import com.danielqueiroz.fooddelivery.domain.service.FotoService;
import com.danielqueiroz.fooddelivery.domain.service.ProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private FotoService fotoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FotoDTOAssembler fotoDTOAssembler;
	
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,@Valid FotoProdutoInput fotoProduto) throws IOException {

		MultipartFile arquivo = fotoProduto.getArquivo();
		Produto produto = produtoService.buscarPorId(restauranteId, produtoId);
		
		FotoProduto foto = new FotoProduto();
		foto.setProduto(produto);
		foto.setDescricao(fotoProduto.getDescricao());
		foto.setContentType(arquivo.getContentType());
		foto.setTamanho(arquivo.getSize());
		foto.setNomeArquivo(arquivo.getOriginalFilename());
		
		FotoProduto fotoSalva = fotoService.salvar(foto, arquivo.getInputStream());
		
		return fotoDTOAssembler.toModel(fotoSalva);
	}
	
}
