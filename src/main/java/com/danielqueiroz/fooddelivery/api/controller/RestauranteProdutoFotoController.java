package com.danielqueiroz.fooddelivery.api.controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.danielqueiroz.fooddelivery.api.model.FotoProdutoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.FotoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.input.FotoProdutoInput;
import com.danielqueiroz.fooddelivery.api.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.danielqueiroz.fooddelivery.core.security.CheckSecurity;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.FotoProduto;
import com.danielqueiroz.fooddelivery.domain.model.Produto;
import com.danielqueiroz.fooddelivery.domain.service.FotoService;
import com.danielqueiroz.fooddelivery.domain.service.FotoStorageService;
import com.danielqueiroz.fooddelivery.domain.service.FotoStorageService.FotoRecuperada;
import com.danielqueiroz.fooddelivery.domain.service.ProdutoService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos/{produtoId}/foto", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

	@Autowired
	private FotoService fotoService;
	
	@Autowired
	private FotoStorageService fotoStorageService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private FotoDTOAssembler fotoDTOAssembler;
	
	@CheckSecurity.Restaurantes.PodeEditarEGerenciar
	@Override
	@PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public FotoProdutoDTO atualizarFoto(@PathVariable Long restauranteId, @PathVariable Long produtoId,@Valid FotoProdutoInput fotoProduto, @RequestPart(required = true) MultipartFile arquivo) throws IOException {

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
	
	@CheckSecurity.Restaurantes.PodeConsultar
	@Override
	@GetMapping
	public FotoProdutoDTO buscar(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId) {
	    FotoProduto fotoProduto = fotoService.buscarPorId(restauranteId, produtoId);
	    return fotoDTOAssembler.toModel(fotoProduto);
	}
	
	@Override
	@GetMapping(produces = MediaType.ALL_VALUE)
	public ResponseEntity<?> buscarFoto(@PathVariable Long restauranteId, 
	        @PathVariable Long produtoId, @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException {
		
		try {
			FotoProduto fotoProduto = fotoService.buscarPorId(restauranteId, produtoId);
			
			MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
			List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
			verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);
			
			FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
			
			if (fotoRecuperada.temUrl()) {
				return ResponseEntity
						.status(HttpStatus.FOUND)
						.header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
						.build();
			} else {
				return ResponseEntity.ok().contentType(mediaTypeFoto)
						.body(new InputStreamResource(fotoRecuperada.getInputStream()));
			}
			
		
		} catch(EntidadeNaoEncontradaException e) {

			return ResponseEntity.notFound().build();
		}
	}
	
	@CheckSecurity.Restaurantes.PodeEditarEGerenciar
	@Override
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
