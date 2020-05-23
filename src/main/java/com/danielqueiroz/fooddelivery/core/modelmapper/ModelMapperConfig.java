package com.danielqueiroz.fooddelivery.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.danielqueiroz.fooddelivery.api.model.EnderecoDTO;
import com.danielqueiroz.fooddelivery.domain.model.Endereco;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();
	
		var toEnderecoDTO = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		toEnderecoDTO.<String>addMapping( 
				src -> src.getCidade().getEstado().getNome(),
				(to, value) -> to.getCidade().setEstado(value));
		
		return modelMapper;
	}
	
	
}
