package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.model.PermissaoDTO;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Permissao;

@Component
public class PermissaoDTOAssembler implements RepresentationModelAssembler<Permissao, PermissaoDTO> {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private CreateLinks createLinks;

	@Autowired
	private UserSecurity userSecurity;

	@Override
	public PermissaoDTO toModel(Permissao permissao) {
		PermissaoDTO permissaoDto = modelMapper.map(permissao, PermissaoDTO.class);
		return permissaoDto;
	}

	@Override
	public CollectionModel<PermissaoDTO> toCollectionModel(Iterable<? extends Permissao> entities) {
		CollectionModel<PermissaoDTO> collectionModel = RepresentationModelAssembler.super.toCollectionModel(entities);
		if (userSecurity.podeConsultarUsuariosGruposPermissoes()) {
			collectionModel.add(createLinks.linkToPermissoes());
		}
		return collectionModel;
	}

}
