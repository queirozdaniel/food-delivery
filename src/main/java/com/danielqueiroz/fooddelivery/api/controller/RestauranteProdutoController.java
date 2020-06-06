package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.ProdutoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.ProdutoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.ProdutoInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.input.ProdutoInput;
import com.danielqueiroz.fooddelivery.domain.model.Produto;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.repository.ProdutoRepository;
import com.danielqueiroz.fooddelivery.domain.service.ProdutoService;
import com.danielqueiroz.fooddelivery.domain.service.RestauranteService;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

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
	    
	    @GetMapping
	    public List<ProdutoDTO> listar(@PathVariable Long restauranteId, @RequestParam(required = false) boolean incluirInativos) {
	        Restaurante restaurante = restauranteService.buscarPorId(restauranteId);
	        
	        List<Produto> todosProdutos;
	        if (incluirInativos) {				
	        	todosProdutos = produtoRepository.findByRestaurante(restaurante);
			} else {
				todosProdutos = produtoRepository.findAtivosByRestaurante(restaurante);
			}
	        
	        return produtoDTOAssembler.toCollectionModel(todosProdutos);
	    }
	    
	    @GetMapping("/{produtoId}")
	    public ProdutoDTO buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId) {
	        Produto produto = produtoService.buscarPorId(restauranteId, produtoId);
	        
	        return produtoDTOAssembler.toModel(produto);
	    }
	    
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
	    
	    @PutMapping("/{produtoId}")
	    public ProdutoDTO atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId,
	            @RequestBody @Valid ProdutoInput produtoInput) {
	        Produto produtoAtual = produtoService.buscarPorId(restauranteId, produtoId);
	        
	        produtoInputDisassembler.copyToDomainObject(produtoInput, produtoAtual);
	        
	        produtoAtual = produtoService.salvar(produtoAtual);
	        
	        return produtoDTOAssembler.toModel(produtoAtual);
	    }
	
}