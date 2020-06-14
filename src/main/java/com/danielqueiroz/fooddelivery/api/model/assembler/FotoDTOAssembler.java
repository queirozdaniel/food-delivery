package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.model.FotoProdutoDTO;
import com.danielqueiroz.fooddelivery.domain.model.FotoProduto;

@Component
public class FotoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public FotoProdutoDTO toModel(FotoProduto fotoProduto) {
        return modelMapper.map(fotoProduto, FotoProdutoDTO.class);
    }
    
	
	
}
