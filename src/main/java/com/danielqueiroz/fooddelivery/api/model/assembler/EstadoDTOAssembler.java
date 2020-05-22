package com.danielqueiroz.fooddelivery.api.model.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.model.EstadoDTO;
import com.danielqueiroz.fooddelivery.domain.model.Estado;

@Component
public class EstadoDTOAssembler {

	@Autowired
	private ModelMapper modelMapper;
	
	 public EstadoDTO toModel(Estado estado) {
	        return modelMapper.map(estado, EstadoDTO.class);
	    }
	    
	    public List<EstadoDTO> toCollectionModel(List<Estado> estados) {
	        return estados.stream()
	                .map(estado -> toModel(estado))
	                .collect(Collectors.toList());
	    }
	
}
