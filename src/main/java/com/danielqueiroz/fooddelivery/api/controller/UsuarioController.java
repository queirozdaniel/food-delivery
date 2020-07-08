package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.UsuarioDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.UsuarioDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.UsuarioInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.input.SenhaInput;
import com.danielqueiroz.fooddelivery.api.model.input.UsuarioComSenhaInput;
import com.danielqueiroz.fooddelivery.api.model.input.UsuarioInput;
import com.danielqueiroz.fooddelivery.api.openapi.controller.UsuarioControllerOpenApi;
import com.danielqueiroz.fooddelivery.domain.model.Usuario;
import com.danielqueiroz.fooddelivery.domain.repository.UsuarioRepository;
import com.danielqueiroz.fooddelivery.domain.service.UsuarioService;

@RestController
@RequestMapping(value = "/usuarios", produces = MediaType.APPLICATION_JSON_VALUE)
public class UsuarioController implements UsuarioControllerOpenApi {

	@Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private UsuarioService usuarioService;
    
    @Autowired
    private UsuarioDTOAssembler usuarioDTOAssembler;
    
    @Autowired
    private UsuarioInputDisassembler usuarioInputDisassembler;
    
    @Override
	@GetMapping
    public CollectionModel<UsuarioDTO> listar() {
        List<Usuario> todasUsuarios = usuarioRepository.findAll();
        
        return usuarioDTOAssembler.toCollectionModel(todasUsuarios);
    }
    
    @Override
	@GetMapping("/{id}")
    public UsuarioDTO buscar(@PathVariable Long id) {
    	System.out.println(id);
        Usuario usuario = usuarioService.buscarPorId(id);
        
        return usuarioDTOAssembler.toModel(usuario);
    }
    
    @Override
	@PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO adicionar(@RequestBody @Valid UsuarioComSenhaInput usuarioInput) {
        Usuario usuario = usuarioInputDisassembler.toDomainObject(usuarioInput);
        
        return usuarioDTOAssembler.toModel(usuarioService.salvar(usuario));
    }
    
    @Override
	@PutMapping("/{id}")
    public UsuarioDTO atualizar(@PathVariable Long id,
            @RequestBody @Valid UsuarioInput usuarioInput) {
        Usuario usuarioAtual = usuarioService.buscarPorId(id);
        
        usuarioInputDisassembler.copyToDomainObject(usuarioInput, usuarioAtual);
        
        return usuarioDTOAssembler.toModel(usuarioService.salvar(usuarioAtual));
    }
    
    @Override
	@PutMapping("/{id}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaInput senha) {
        usuarioService.alterarSenha(id, senha.getSenhaAtual(), senha.getNovaSenha());
    }
	
}
