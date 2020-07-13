package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.CreateLinks;
import com.danielqueiroz.fooddelivery.api.controller.RestauranteProdutoController;
import com.danielqueiroz.fooddelivery.api.model.ProdutoDTO;
import com.danielqueiroz.fooddelivery.domain.model.Produto;

@Component
public class ProdutoDTOAssembler extends RepresentationModelAssemblerSupport<Produto, ProdutoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CreateLinks createLinks;
	
	public ProdutoDTOAssembler() {
		super(RestauranteProdutoController.class, ProdutoDTO.class);
	}

	@Override
    public ProdutoDTO toModel(Produto produto) {
    	ProdutoDTO produtoDto = createModelWithId(produto.getId(), produto, produto.getRestaurante().getId());
    	modelMapper.map(produto, produtoDto);
    
    	produtoDto.add(createLinks.linkToProdutos(produto.getRestaurante().getId(), "produtos"));
    	
    	produtoDto.add(createLinks.linkToFotoProduto(
                produto.getRestaurante().getId(), produto.getId(), "foto"));
    	
    	return produtoDto;
    }
    
}
