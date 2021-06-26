package com.danielqueiroz.fooddelivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private CreateLinks createLinks;
	
	@Autowired
	private UserSecurity userSecurity;

	@GetMapping("/")
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		
		if (userSecurity.podeConsultarCozinhas()) {
			rootEntryPointModel.add(createLinks.linkToCozinhas("cozinhas"));
	    }
	    
	    if (userSecurity.podePesquisarPedidos()) {
	    	rootEntryPointModel.add(createLinks.linksToPedidos("pedidos"));
	    }
	    
	    if (userSecurity.podeConsultarRestaurantes()) {
	    	rootEntryPointModel.add(createLinks.linkToRestaurantes("restaurantes"));
	    }
	    
	    if (userSecurity.podeConsultarUsuariosGruposPermissoes()) {
	    	rootEntryPointModel.add(createLinks.linkToGrupos("grupos"));
	    	rootEntryPointModel.add(createLinks.linkToUsuarios("usuarios"));
	    	rootEntryPointModel.add(createLinks.linkToPermissoes("permissoes"));
	    }
	    
	    if (userSecurity.podeConsultarFormasPagamento()) {
	    	rootEntryPointModel.add(createLinks.linkToFormasPagamento("formas-pagamento"));
	    }
	    
	    if (userSecurity.podeConsultarEstados()) {
	    	rootEntryPointModel.add(createLinks.linkToEstados("estado"));
	    }
	    
	    if (userSecurity.podeConsultarCidades()) {
	    	rootEntryPointModel.add(createLinks.linkToCidades("cidades"));
	    }
	    
	    if (userSecurity.podeConsultarEstatisticas()) {
	    	rootEntryPointModel.add(createLinks.linkToEstatisticas("estatisticas"));
	    }
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
		
	}
}
