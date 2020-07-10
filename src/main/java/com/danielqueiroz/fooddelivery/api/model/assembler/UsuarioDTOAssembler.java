package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.CreateLinks;
import com.danielqueiroz.fooddelivery.api.controller.UsuarioController;
import com.danielqueiroz.fooddelivery.api.model.UsuarioDTO;
import com.danielqueiroz.fooddelivery.domain.model.Usuario;

@Component
public class UsuarioDTOAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CreateLinks usuarioLinks;
	
	public UsuarioDTOAssembler() {
		super(UsuarioController.class, UsuarioDTO.class);
	}

    
    public UsuarioDTO toModel(Usuario usuario) {
    
    	UsuarioDTO usuarioDto = createModelWithId(usuario.getId(),usuario);
    	modelMapper.map(usuario, usuarioDto);
    	
    	usuarioDto.add(usuarioLinks.linkToUsuarios("usuarios"));
    	
    	usuarioDto.add(usuarioLinks.linkToGruposUsuario(usuario.getId(), "grupos-usuario"));
        
    			
    	return usuarioDto;
    }
    
    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel());
    }  
    
}
