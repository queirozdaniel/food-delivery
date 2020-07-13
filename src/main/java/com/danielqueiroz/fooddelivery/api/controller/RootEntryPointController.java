package com.danielqueiroz.fooddelivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.CreateLinks;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class RootEntryPointController {

	@Autowired
	private CreateLinks createLinks;
	
	@GetMapping
	public RootEntryPointModel root() {
		var rootEntryPointModel = new RootEntryPointModel();
		
		rootEntryPointModel.add(createLinks.linkToCozinhas("cozinhas"));
		rootEntryPointModel.add(createLinks.linksToPedidos("pedidos"));
		rootEntryPointModel.add(createLinks.linkToRestaurantes("restaurantes"));
		rootEntryPointModel.add(createLinks.linkToFormasPagamento("formas-pagamento"));
		rootEntryPointModel.add(createLinks.linkToGrupos("grupos"));
		rootEntryPointModel.add(createLinks.linkToUsuarios("usuarios"));
		rootEntryPointModel.add(createLinks.linkToPermissoes("permissoes"));
		rootEntryPointModel.add(createLinks.linkToEstados("estado"));
		rootEntryPointModel.add(createLinks.linkToCidades("cidades"));
		rootEntryPointModel.add(createLinks.linkToEstatisticas("estatisticas"));
		
		return rootEntryPointModel;
	}
	
	private static class RootEntryPointModel extends RepresentationModel<RootEntryPointModel> {
		
	}
}
