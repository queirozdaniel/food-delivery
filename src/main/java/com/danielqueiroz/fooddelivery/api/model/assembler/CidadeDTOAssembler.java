package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.CreateLinks;
import com.danielqueiroz.fooddelivery.api.controller.CidadeController;
import com.danielqueiroz.fooddelivery.api.model.CidadeDTO;
import com.danielqueiroz.fooddelivery.domain.model.Cidade;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CreateLinks cidadeLinks;

	public CidadeDTOAssembler() {
		super(CidadeController.class, CidadeDTO.class);
	}
	
	public CidadeDTO toModel(Cidade cidade) {
		
		CidadeDTO cidadeDto  = createModelWithId(cidade.getId(), cidade);
		modelMapper.map(cidade, cidadeDto);
		
		cidadeDto.add(cidadeLinks.linkToCidades("cidades"));
		
		cidadeDto.getEstado().add(cidadeLinks.linkToEstado(cidade.getEstado().getId()));
		
        return cidadeDto;
    }
    
	
	@Override
	public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> entities) {
		return super.toCollectionModel(entities).add(cidadeLinks.linkToCidades());
	}
	
}
