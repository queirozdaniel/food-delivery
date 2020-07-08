package com.danielqueiroz.fooddelivery.api.model.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import com.danielqueiroz.fooddelivery.api.controller.UsuarioController;
import com.danielqueiroz.fooddelivery.api.controller.UsuarioGrupoController;
import com.danielqueiroz.fooddelivery.api.model.UsuarioDTO;
import com.danielqueiroz.fooddelivery.domain.model.Usuario;

@Component
public class UsuarioDTOAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioDTO> {

	@Autowired
	private ModelMapper modelMapper;
    
	public UsuarioDTOAssembler() {
		super(UsuarioController.class, UsuarioDTO.class);
	}

    
    public UsuarioDTO toModel(Usuario usuario) {
    
    	UsuarioDTO usuarioDto = modelMapper.map(usuario, UsuarioDTO.class);
    	
    	usuarioDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
    			.methodOn(UsuarioController.class)
    			.buscar(usuarioDto.getId()))
    			.withSelfRel());
    	
    	usuarioDto.add(WebMvcLinkBuilder
    			.linkTo(UsuarioController.class)
    			.withRel("usuarios"));
        
    	usuarioDto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder
    			.methodOn(UsuarioGrupoController.class)
                .listar(usuario.getId()))
    			.withRel("grupos-usuario"));
    			
    	return usuarioDto;
    }
    
    @Override
    public CollectionModel<UsuarioDTO> toCollectionModel(Iterable<? extends Usuario> entities) {
        return super.toCollectionModel(entities)
            .add(WebMvcLinkBuilder.linkTo(UsuarioController.class).withSelfRel());
    }  
    
}
