package com.danielqueiroz.fooddelivery.core.modelmapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.danielqueiroz.fooddelivery.api.model.EnderecoDTO;
import com.danielqueiroz.fooddelivery.api.model.input.ItemPedidoInput;
import com.danielqueiroz.fooddelivery.domain.model.Endereco;
import com.danielqueiroz.fooddelivery.domain.model.ItemPedido;

@Configuration
public class ModelMapperConfig {

	@Bean
	public ModelMapper modelMapper() {
		var modelMapper = new ModelMapper();

		modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
			.addMappings(mapper -> mapper.skip(ItemPedido::setId));
		
		var toEnderecoDTO = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
		
		toEnderecoDTO.<String>addMapping(enderecoModel -> enderecoModel.getCidade().getEstado().getNome(),
				(enderecoDTO, value) -> enderecoDTO.getCidade().setEstado(value));

		return modelMapper;
	}

}
