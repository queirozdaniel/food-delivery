package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.CidadeController;
import com.danielqueiroz.fooddelivery.api.controller.EstadoController;
import com.danielqueiroz.fooddelivery.api.model.CidadeDTO;
import com.danielqueiroz.fooddelivery.domain.model.Cidade;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO>{

	@Autowired
	private ModelMapper modelMapper;

	public CidadeDTOAssembler() {
		super(CidadeController.class, CidadeDTO.class);
	}
	
	public CidadeDTO toModel(Cidade cidade) {
		
//		Maneira alternativa para criar os links self
//		CidadeDTO cidadeDto  = createModelWithId(cidade.getId(), cidade);
//		modelMapper.map(cidade, cidadeDto);
//		
		
		CidadeDTO cidadeDto  = modelMapper.map(cidade, CidadeDTO.class);
		
		cidadeDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(CidadeController.class)
				.buscarPorId(cidadeDto.getId()))
				.withSelfRel());
		
		cidadeDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(CidadeController.class)
				.buscarTodos())
				.withRel("cidades"));
		
		cidadeDto.getEstado().add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
				.methodOn(EstadoController.class)
				.buscarPorId(cidadeDto.getEstado().getId()))
				.withSelfRel());
		
		
        return cidadeDto;
    }
    
	
	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities).add(WebMvcLinkBuilder.linkTo(CidadeController.class).withSelfRel());
	}
	
}
