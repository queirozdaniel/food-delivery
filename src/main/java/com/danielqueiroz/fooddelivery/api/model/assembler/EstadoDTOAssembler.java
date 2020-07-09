package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.EstadoController;
import com.danielqueiroz.fooddelivery.api.model.EstadoDTO;
import com.danielqueiroz.fooddelivery.domain.model.Estado;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	public EstadoDTOAssembler() {
		super(EstadoController.class, EstadoDTO.class);
	}

	@Override
    public EstadoDTO toModel(Estado estado) {
		EstadoDTO estadoDto = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoDto);
        
        estadoDto.add(WebMvcLinkBuilder.linkTo(EstadoController.class).withRel("estados"));
        
        return estadoDto;
    }
    
    @Override
    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
        return super.toCollectionModel(entities)
            .add(WebMvcLinkBuilder.linkTo(EstadoController.class).withSelfRel());
    } 
}
