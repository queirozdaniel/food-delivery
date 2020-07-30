package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.CidadeController;
import com.danielqueiroz.fooddelivery.api.model.CidadeDTO;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Cidade;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CreateLinks createLinks;
	
	@Autowired
	private UserSecurity userSecurity;

	public CidadeDTOAssembler() {
		super(CidadeController.class, CidadeDTO.class);
	}
	
	public CidadeDTO toModel(Cidade cidade) {
		CidadeDTO cidadeDto  = createModelWithId(cidade.getId(), cidade);
		modelMapper.map(cidade, cidadeDto);

		if (userSecurity.podeConsultarCidades()) {
			cidadeDto.add(createLinks.linkToCidades("cidades"));
		}
		
		if (userSecurity.podeConsultarEstados()) {
			cidadeDto.getEstado().add(createLinks.linkToEstado(cidade.getEstado().getId()));
		}
		
        return cidadeDto;
    }
    
	
	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		CollectionModel<CidadeDTO> collectionModel = super.toCollectionModel(entities);
	    if (userSecurity.podeConsultarCidades()) {
	        collectionModel.add(createLinks.linkToCidades());
	    }
	    return collectionModel;
	}
	
}
