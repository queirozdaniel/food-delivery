package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.CozinhaController;
import com.danielqueiroz.fooddelivery.api.model.CozinhaDTO;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Cozinha;

@Component
public class CozinhaDTOAssembler extends RepresentationModelAssemblerSupport<Cozinha, CozinhaDTO>{

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CreateLinks createLinks;

	@Autowired
	private UserSecurity userSecurity;
	
	public CozinhaDTOAssembler() {
		super(CozinhaController.class, CozinhaDTO.class);
	}

	public CozinhaDTO toModel(Cozinha cozinha) {
		CozinhaDTO cozinhaDto = createModelWithId(cozinha.getId(), cozinha);
		modelMapper.map(cozinha, cozinhaDto);
		
		if (userSecurity.podeConsultarCozinhas()) {
			cozinhaDto.add(createLinks.linkToCozinhas("cozinhas"));
		}
		
        return cozinhaDto;
    }
}
