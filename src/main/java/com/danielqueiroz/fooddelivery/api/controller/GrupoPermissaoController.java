package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.PermissaoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.PermissaoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.danielqueiroz.fooddelivery.domain.model.Grupo;
import com.danielqueiroz.fooddelivery.domain.service.GrupoService;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	  @Autowired
	    private GrupoService grupoService;
	    
	    @Autowired
	    private PermissaoDTOAssembler permissaoDTOAssembler;
	    
	    @Override
		@GetMapping
	    public List<PermissaoDTO> listar(@PathVariable Long grupoId) {
	        Grupo grupo = grupoService.buscarPorId(grupoId);
	        
	        return permissaoDTOAssembler.toCollectionModel(grupo.getPermissoes());
	    }
	    
	    @Override
		@DeleteMapping("/{permissaoId}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
	        grupoService.desassociarPermissao(grupoId, permissaoId);
	    }
	    
	    @Override
		@PutMapping("/{permissaoId}")
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
	        grupoService.associarPermissao(grupoId, permissaoId);
	    }     
	
}
