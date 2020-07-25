package com.danielqueiroz.fooddelivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.CreateLinks;
import com.danielqueiroz.fooddelivery.api.model.GrupoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.GrupoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.danielqueiroz.fooddelivery.core.security.CheckSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Usuario;
import com.danielqueiroz.fooddelivery.domain.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos",  produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

	 @Autowired
	    private UsuarioService usuarioService;
	    
	    @Autowired
	    private GrupoDTOAssembler grupoDTOAssembler;
	    
	    @Autowired
	    private CreateLinks createLinks; 
	    
	    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	    @Override
		@GetMapping
	    public CollectionModel<GrupoDTO> listar(@PathVariable Long usuarioId) {
	        Usuario usuario = usuarioService.buscarPorId(usuarioId);
	        
	        CollectionModel<GrupoDTO> gruposDto = grupoDTOAssembler.toCollectionModel(usuario.getGrupos())
	                .removeLinks()
	                .add(createLinks.linkToUsuarioGrupoAssociacao(usuarioId, "associar"));
	        
	        gruposDto.getContent().forEach(grupoModel -> {
	            grupoModel.add(createLinks.linkToUsuarioGrupoDesassociacao(
	                    usuarioId, grupoModel.getId(), "desassociar"));
	        });
	        
	        return gruposDto;
	    }
	    
	    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	    @Override
		@DeleteMapping("/{grupoId}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
	        usuarioService.desassociarGrupo(usuarioId, grupoId);
	        return ResponseEntity.noContent().build();
	    }
	    
	    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	    @Override
		@PutMapping("/{grupoId}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
	        usuarioService.associarGrupo(usuarioId, grupoId);
	        return ResponseEntity.noContent().build();
	    }   
	
	
}
