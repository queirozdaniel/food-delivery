package com.danielqueiroz.fooddelivery.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.CreateLinks;
import com.danielqueiroz.fooddelivery.api.model.UsuarioDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.UsuarioDTOAssembler;
import com.danielqueiroz.fooddelivery.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.danielqueiroz.fooddelivery.domain.model.Restaurante;
import com.danielqueiroz.fooddelivery.domain.service.RestauranteService;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/responsaveis", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

	@Autowired
	private RestauranteService restauranteService;

	@Autowired
	private UsuarioDTOAssembler usuarioDTOAssembler;
	
	@Autowired
	private CreateLinks responsavelLinks;

	@Override
	@GetMapping
	public CollectionModel<UsuarioDTO> listar(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarPorId(restauranteId);

		return usuarioDTOAssembler.toCollectionModel(restaurante.getResponsaveis())
				.removeLinks()
				.add(responsavelLinks.linkToResponsaveisRestaurante(restauranteId));
	}

	@Override
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desassociarResponsavel(restauranteId, usuarioId);
	}

	@Override
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.associarResponsavel(restauranteId, usuarioId);
	}

}
