package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.GrupoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.GrupoDTOAssembler;
import com.danielqueiroz.fooddelivery.domain.model.Usuario;
import com.danielqueiroz.fooddelivery.domain.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

	 @Autowired
	    private UsuarioService usuarioService;
	    
	    @Autowired
	    private GrupoDTOAssembler grupoDTOAssembler;
	    
	    @GetMapping
	    public List<GrupoDTO> listar(@PathVariable Long usuarioId) {
	        Usuario usuario = usuarioService.buscarPorId(usuarioId);
	        
	        return grupoDTOAssembler.toCollectionModel(usuario.getGrupos());
	    }
	    
	    @DeleteMapping("/{grupoId}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
	        usuarioService.desassociarGrupo(usuarioId, grupoId);
	    }
	    
	    @PutMapping("/{grupoId}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void associar(@PathVariable Long usuarioId, @PathVariable Long grupoId) {
	        usuarioService.associarGrupo(usuarioId, grupoId);
	    }   
	
	
}
