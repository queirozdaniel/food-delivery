package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.CreateLinks;
import com.danielqueiroz.fooddelivery.api.controller.RestauranteProdutoFotoController;
import com.danielqueiroz.fooddelivery.api.model.FotoProdutoDTO;
import com.danielqueiroz.fooddelivery.domain.model.FotoProduto;

@Component
public class FotoDTOAssembler extends RepresentationModelAssemblerSupport<FotoProduto, FotoProdutoDTO>{
	
	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CreateLinks createLinks;
	
	public FotoDTOAssembler() {
		super(RestauranteProdutoFotoController.class, FotoProdutoDTO.class);
	}
	
	@Override
	public FotoProdutoDTO toModel(FotoProduto fotoProduto) {
		FotoProdutoDTO fotoProdutoDto = modelMapper.map(fotoProduto, FotoProdutoDTO.class);
		
		fotoProdutoDto.add(createLinks.linkToFotoProduto(
				fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId()));
        
		fotoProdutoDto.add(createLinks.linkToProduto(
				fotoProduto.getRestauranteId(), fotoProduto.getProduto().getId(), "produto"));
	
        return fotoProdutoDto;
	}
    
}
