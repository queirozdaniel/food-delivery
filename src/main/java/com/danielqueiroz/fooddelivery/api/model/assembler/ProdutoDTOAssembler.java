package com.danielqueiroz.fooddelivery.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.model.ProdutoDTO;
import com.danielqueiroz.fooddelivery.domain.model.Produto;

@Component
public class ProdutoDTOAssembler {

	@Autowired
    private ModelMapper modelMapper;
    
    public ProdutoDTO toModel(Produto produto) {
        return modelMapper.map(produto, ProdutoDTO.class);
    }
    
    public List<ProdutoDTO> toCollectionModel(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toModel(produto))
                .collect(Collectors.toList());
    }
	
}
