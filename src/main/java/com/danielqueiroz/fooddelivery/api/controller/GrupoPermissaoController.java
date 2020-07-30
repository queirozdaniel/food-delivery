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

import com.danielqueiroz.fooddelivery.api.model.PermissaoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.PermissaoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.danielqueiroz.fooddelivery.api.utils.CreateLinks;
import com.danielqueiroz.fooddelivery.core.security.CheckSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Grupo;
import com.danielqueiroz.fooddelivery.domain.service.GrupoService;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissoes", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

	@Autowired
	private GrupoService grupoService;

	@Autowired
	private PermissaoDTOAssembler permissaoDTOAssembler;

	@Autowired
	private CreateLinks createLinks;

	@CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
	@Override
	@GetMapping
	public CollectionModel<PermissaoDTO> buscarTodos(@PathVariable Long grupoId) {
		Grupo grupo = grupoService.buscarPorId(grupoId);

		CollectionModel<PermissaoDTO> permissoesDto = permissaoDTOAssembler.toCollectionModel(grupo.getPermissoes())
				.removeLinks().add(createLinks.linkToGrupoPermissoes(grupoId))
				.add(createLinks.linkToGrupoPermissaoAssociacao(grupoId, "associar"));

		permissoesDto.getContent().forEach(permissaoDto -> {
			permissaoDto
					.add(createLinks.linkToGrupoPermissaoDesassociacao(grupoId, permissaoDto.getId(), "desassociar"));
		});

		return permissoesDto;
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@DeleteMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.desassociarPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}

	@CheckSecurity.UsuariosGruposPermissoes.PodeEditar
	@Override
	@PutMapping("/{permissaoId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId) {
		grupoService.associarPermissao(grupoId, permissaoId);
		return ResponseEntity.noContent().build();
	}

}
