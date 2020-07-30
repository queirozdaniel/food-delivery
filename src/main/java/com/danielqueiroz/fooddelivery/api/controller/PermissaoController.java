package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.PermissaoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.PermissaoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.openapi.controller.PermissaoControllerOpenApi;
import com.danielqueiroz.fooddelivery.core.security.CheckSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Permissao;
import com.danielqueiroz.fooddelivery.domain.repository.PermissaoRepository;

@RestController
@RequestMapping(path = "/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PermissaoController implements PermissaoControllerOpenApi {

    @Autowired
    private PermissaoRepository permissaoRepository;
    
    @Autowired
    private PermissaoDTOAssembler permissaoDTOAssembler;
	
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@GetMapping
	public CollectionModel<PermissaoDTO> buscarTodos() {
		List<Permissao> todasPermissoes = permissaoRepository.findAll();
        
        return permissaoDTOAssembler.toCollectionModel(todasPermissoes);
	}

}
