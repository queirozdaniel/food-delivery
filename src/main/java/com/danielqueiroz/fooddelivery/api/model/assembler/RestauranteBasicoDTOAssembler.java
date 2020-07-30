package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.RestauranteController;
import com.danielqueiroz.fooddelivery.api.model.RestauranteBasicoDTO;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;

@Component
public class RestauranteBasicoDTOAssembler 
        extends RepresentationModelAssemblerSupport<Restaurante, RestauranteBasicoDTO> {

    @Autowired
    private ModelMapper modelMapper;
    
    @Autowired
    private CreateLinks createLinks;
    
    @Autowired
	private UserSecurity userSecurity; 
    
    public RestauranteBasicoDTOAssembler() {
        super(RestauranteController.class, RestauranteBasicoDTO.class);
    }
    
    @Override
    public RestauranteBasicoDTO toModel(Restaurante restaurante) {
    	RestauranteBasicoDTO restauranteModel = createModelWithId(
                restaurante.getId(), restaurante);
        modelMapper.map(restaurante, restauranteModel);

        if (userSecurity.podeConsultarRestaurantes()) {
        	restauranteModel.add(createLinks.linkToRestaurantes("restaurantes"));
        }
        
        if (userSecurity.podeConsultarCozinhas()) {
        	restauranteModel.getCozinha().add(
        			createLinks.linkToCozinha(restaurante.getCozinha().getId()));
        }
        
        return restauranteModel;
    }
    
    @Override
    public CollectionModel<RestauranteBasicoDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
    	CollectionModel<RestauranteBasicoDTO> collectionModel = super.toCollectionModel(entities);
        if (userSecurity.podeConsultarRestaurantes()) {
            collectionModel.add(createLinks.linkToRestaurantes());
        }
        return collectionModel;
    }   
}  