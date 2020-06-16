package com.danielqueiroz.fooddelivery.api.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.danielqueiroz.fooddelivery.api.model.FotoProdutoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.FotoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.input.FotoProdutoInput;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.FotoProduto;
import com.danielqueiroz.fooddelivery.domain.model.Produto;
import com.danielqueiroz.fooddelivery.domain.service.FotoService;
import com.danielqueiroz.fooddelivery.domain.service.FotoStorageService;
import com.danielqueiroz.fooddelivery.domain.service.ProdutoService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

	@Autowired
	private FotoService fotoService;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
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
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId) {
	    FotoProduto fotoProduto = fotoService.buscarPorId(restauranteId, produtoId);
	    return fotoDTOAssembler.toModel(fotoProduto);
	}
	
	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<InputStreamResource> buscarFoto(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		
		try {
			FotoProduto fotoProduto = fotoService.buscarPorId(restauranteId, produtoId);
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
			
			InputStream inputStream = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			return ResponseEntity.ok().contentType(mediaTypeFoto).body(new InputStreamResource(inputStream));
		
		} catch(EntidadeNaoEncontradaException e) {

			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void excluir(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId) {
		fotoService.excluir(restauranteId, produtoId);
	}  

	private void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
		boolean compativel = mediaTypesAceitas.stream()
					.anyMatch(mediaTypesAceita -> mediaTypesAceita.isCompatibleWith(mediaTypeFoto));
		
		if (!compativel) {
			throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
		}
		
	}
	
	
}
