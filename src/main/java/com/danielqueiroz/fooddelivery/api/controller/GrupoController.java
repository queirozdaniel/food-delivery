package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.GrupoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.GrupoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.GrupoInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.input.GrupoInput;
import com.danielqueiroz.fooddelivery.api.openapi.controller.GrupoControllerOpenApi;
import com.danielqueiroz.fooddelivery.core.security.CheckSecurity;
import com.danielqueiroz.fooddelivery.domain.model.Grupo;
import com.danielqueiroz.fooddelivery.domain.service.GrupoService;

@RestController
@RequestMapping(value = "/grupos", produces = MediaType.APPLICATION_JSON_VALUE)
public class GrupoController implements GrupoControllerOpenApi {
   
    @Autowired
    private GrupoService grupoService;
    
    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;
    
    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
	@GetMapping
    public CollectionModel<GrupoDTO> buscarTodos() {
        List<Grupo> todosGrupos = grupoService.buscarTodos();
        
        return grupoDTOAssembler.toCollectionModel(todosGrupos);
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @Override
	@GetMapping("/{id}")
    public GrupoDTO buscarPorId(@PathVariable Long id) {
        Grupo grupo = grupoService.buscarPorId(id);
        
        return grupoDTOAssembler.toModel(grupo);
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
        
        return grupoDTOAssembler.toModel(grupoService.salvar(grupo));
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
	@PutMapping("/{id}")
    public GrupoDTO atualizar(@PathVariable Long id,
            @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = grupoService.buscarPorId(id);
        
        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
        
        return grupoDTOAssembler.toModel(grupoService.salvar(grupoAtual));
    }
    
    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @Override
	@DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable Long id) {
        grupoService.deletar(id);	
    }  
	
	
}
