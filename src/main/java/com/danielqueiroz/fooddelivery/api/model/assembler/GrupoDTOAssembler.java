package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.GrupoController;
import com.danielqueiroz.fooddelivery.api.model.GrupoDTO;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Grupo;

@Component
public class GrupoDTOAssembler extends RepresentationModelAssemblerSupport<Grupo, GrupoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CreateLinks createLinks;

	@Autowired
	private UserSecurity userSecurity;

	public GrupoDTOAssembler() {
		super(GrupoController.class, GrupoDTO.class);
	}

	@Override
	public GrupoDTO toModel(Grupo grupo) {
		GrupoDTO grupoDto = createModelWithId(grupo.getId(), grupo);
		modelMapper.map(grupo, grupoDto);

		if (userSecurity.podeConsultarUsuariosGruposPermissoes()) {
			grupoDto.add(createLinks.linkToGrupos("grupos"));

			grupoDto.add(createLinks.linkToGrupoPermissoes(grupo.getId(), "permissoes"));
		}

		return grupoDto;
	}

	@Override
	public CollectionModel<GrupoDTO> toCollectionModel(Iterable<? extends Grupo> entities) {
		CollectionModel<GrupoDTO> collectionModel = super.toCollectionModel(entities);

		if (userSecurity.podeConsultarUsuariosGruposPermissoes()) {
			collectionModel.add(createLinks.linkToGrupos());
		}

		return collectionModel;
	}

}
