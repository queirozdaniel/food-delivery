package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import com.danielqueiroz.fooddelivery.domain.model.Grupo;
import com.danielqueiroz.fooddelivery.domain.service.GrupoService;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

   
    @Autowired
    private GrupoService grupoService;
    
    @Autowired
    private GrupoDTOAssembler grupoDTOAssembler;
    
    @Autowired
    private GrupoInputDisassembler grupoInputDisassembler;
    
    @GetMapping
    public List<GrupoDTO> listar() {
        List<Grupo> todosGrupos = grupoService.buscarTodos();
        
        return grupoDTOAssembler.toCollectionModel(todosGrupos);
    }
    
    @GetMapping("/{grupoId}")
    public GrupoDTO buscar(@PathVariable Long id) {
        Grupo grupo = grupoService.buscarPorId(id);
        
        return grupoDTOAssembler.toModel(grupo);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO adicionar(@RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupo = grupoInputDisassembler.toDomainObject(grupoInput);
        
        return grupoDTOAssembler.toModel(grupoService.salvar(grupo));
    }
    
    @PutMapping("/{grupoId}")
    public GrupoDTO atualizar(@PathVariable Long id,
            @RequestBody @Valid GrupoInput grupoInput) {
        Grupo grupoAtual = grupoService.buscarPorId(id);
        
        grupoInputDisassembler.copyToDomainObject(grupoInput, grupoAtual);
        
        return grupoDTOAssembler.toModel(grupoService.salvar(grupoAtual));
    }
    
    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long id) {
        grupoService.deletar(id);	
    }  
	
	
}