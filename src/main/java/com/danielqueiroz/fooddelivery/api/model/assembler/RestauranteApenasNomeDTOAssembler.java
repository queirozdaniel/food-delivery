package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.RestauranteController;
import com.danielqueiroz.fooddelivery.api.model.RestauranteApenasNomeDTO;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;

@Component
public class RestauranteApenasNomeDTOAssembler
		extends RepresentationModelAssemblerSupport<Restaurante, RestauranteApenasNomeDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CreateLinks createLinks;

	@Autowired
	private UserSecurity userSecurity;

	public RestauranteApenasNomeDTOAssembler() {
		super(RestauranteController.class, RestauranteApenasNomeDTO.class);
	}

	@Override
	public RestauranteApenasNomeDTO toModel(Restaurante restaurante) {
		RestauranteApenasNomeDTO restauranteModel = createModelWithId(restaurante.getId(), restaurante);
		modelMapper.map(restaurante, restauranteModel);

		if (userSecurity.podeConsultarRestaurantes()) {
			restauranteModel.add(createLinks.linkToRestaurantes("restaurantes"));
		}

		return restauranteModel;
	}

	@Override
	public CollectionModel<RestauranteApenasNomeDTO> toCollectionModel(Iterable<? extends Restaurante> entities) {
		CollectionModel<RestauranteApenasNomeDTO> collectionModel = super.toCollectionModel(entities);
		if (userSecurity.podeConsultarRestaurantes()) {
			collectionModel.add(createLinks.linkToRestaurantes());
		}
		return collectionModel;
	}
}