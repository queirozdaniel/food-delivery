package com.danielqueiroz.fooddelivery.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.model.CozinhaDTO;
import com.danielqueiroz.fooddelivery.domain.model.Cozinha;

@Component
public class CozinhaDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	public CozinhaDTO toModel(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }
    
    public List<CozinhaDTO> toCollectionModel(List<Cozinha> cozinhas) {
        return cozinhas.stream()
                .map(cozinha -> toModel(cozinha))
                .collect(Collectors.toList());
    }  
	
	
}
