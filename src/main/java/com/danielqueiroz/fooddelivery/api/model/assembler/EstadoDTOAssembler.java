package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.EstadoController;
import com.danielqueiroz.fooddelivery.api.model.EstadoDTO;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Estado;

@Component
public class EstadoDTOAssembler extends RepresentationModelAssemblerSupport<Estado, EstadoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CreateLinks createLinks;
	
	@Autowired
	private UserSecurity userSecurity;

	public EstadoDTOAssembler() {
		super(EstadoController.class, EstadoDTO.class);
	}

	@Override
    public EstadoDTO toModel(Estado estado) {
		EstadoDTO estadoDto = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, estadoDto);
        
        if (userSecurity.podeConsultarEstados()) {
        	estadoDto.add(createLinks.linkToEstados("estados"));
		}
        
        return estadoDto;
    }
    
    @Override
    public CollectionModel<EstadoDTO> toCollectionModel(Iterable<? extends Estado> entities) {
    	CollectionModel<EstadoDTO> collectionModel = super.toCollectionModel(entities);
        if (userSecurity.podeConsultarEstados()) {
            collectionModel.add(createLinks.linkToEstados());
        }
        return collectionModel;
    } 
}
