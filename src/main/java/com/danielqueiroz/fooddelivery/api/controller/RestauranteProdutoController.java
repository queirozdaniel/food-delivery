package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.CreateLinks;
import com.danielqueiroz.fooddelivery.api.model.ProdutoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.ProdutoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.ProdutoInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.input.ProdutoInput;
import com.danielqueiroz.fooddelivery.api.openapi.controller.RestauranteProdutoControllerOpenApi;
import com.danielqueiroz.fooddelivery.domain.model.Produto;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.repository.ProdutoRepository;
import com.danielqueiroz.fooddelivery.domain.service.ProdutoService;
import com.danielqueiroz.fooddelivery.domain.service.RestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/produtos", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteProdutoController implements RestauranteProdutoControllerOpenApi {

	 	@Autowired
	    private ProdutoRepository produtoRepository;
	    
	    @Autowired
	    private ProdutoService produtoService;
	    
	    @Autowired
	    private RestauranteService restauranteService;
	    
	    @Autowired
	    private ProdutoDTOAssembler produtoDTOAssembler;
	    
	    @Autowired
	    private ProdutoInputDisassembler produtoInputDisassembler;
	    
	    @Autowired
		private CreateLinks createLinks;
	    
	    @Override
		@GetMapping
	    public CollectionModel<ProdutoDTO> listar(@PathVariable Long restauranteId, @RequestParam(required = false) Boolean incluirInativos) {
	        Restaurante restaurante = restauranteService.buscarPorId(restauranteId);
	        
	        List<Produto> todosProdutos = null;
	        
	        if (incluirInativos) {				
	        	todosProdutos = produtoRepository.findByRestaurante(restaurante);
			} else {
				todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
			}
	        
	        return produtoDTOAssembler.toCollectionModel(todosProdutos).add(createLinks.linkToProdutos(restauranteId));
	    }
	    
	    @Override
		@GetMapping("/{produtoId}")
	    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
	        Produto produto = produtoService.buscarPorId(restauranteId, produtoId);
	        
	        return produtoDTOAssembler.toModel(produto);
	    }
	    
	    @Override
		@PostMapping
	    @ResponseStatus(HttpStatus.CREATED)
	    public ProdutoDTO adicionar(@PathVariable Long restauranteId,
	            @RequestBody @Valid ProdutoInput produtoInput) {
	        Restaurante restaurante = restauranteService.buscarPorId(restauranteId);
	        
	        Produto produto = produtoInputDisassembler.toDomainObject(produtoInput);
	        produto.setRestaurante(restaurante);
	        
	        produto = produtoService.salvar(produto);
	        
	        return produtoDTOAssembler.toModel(produto);
	    }
	    
	    @Override
		@PutMapping("/{produtoId}")
	    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
	            @RequestBody @Valid ProdutoInput produtoInput) {
	        Produto produtoAtual = produtoService.buscarPorId(restauranteId, produtoId);
	        
	        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
	        
	        produtoAtual = produtoService.salvar(produtoAtual);
	        
	        return produtoDTOAssembler.toModel(produtoAtual);
	    }
	
}
