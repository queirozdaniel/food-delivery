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

import com.danielqueiroz.fooddelivery.api.model.UsuarioDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.UsuarioDTOAssembler;
import com.danielqueiroz.fooddelivery.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.UserSecurity;
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
	
	@Autowired
	private UserSecurity userSecurity;

	@Override
	@GetMapping
	public CollectionModel<UsuarioDTO> buscarTodos(@PathVariable Long restauranteId) {
		Restaurante restaurante = restauranteService.buscarPorId(restauranteId);

		CollectionModel<UsuarioDTO> usuariosDto = usuarioDTOAssembler.toCollectionModel(restaurante.getResponsaveis())
				.removeLinks();
		usuariosDto.add(responsavelLinks.linkToResponsaveisRestaurante(restauranteId));

		if (userSecurity.podeGerenciarCadastroRestaurantes()) {
			usuariosDto.add(responsavelLinks.linkToRestauranteResponsavelAssociacao(restauranteId, "associar"));
			
			usuariosDto.getContent().stream().forEach(usuario -> {
				usuariosDto.add(responsavelLinks.linkToRestauranteResponsavelDesassociacao(restauranteId, usuario.getId(), "desassociar"));
			});
		}
		return usuariosDto;
	}

	@Override
	@DeleteMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.desassociarResponsavel(restauranteId, usuarioId);
		
		return ResponseEntity.noContent().build();
	}

	@Override
	@PutMapping("/{usuarioId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId) {
		restauranteService.associarResponsavel(restauranteId, usuarioId);
		return ResponseEntity.noContent().build();
	}

}
