package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.api.model.EstadoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.EstadoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.EstadoInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.input.EstadoInput;
import com.danielqueiroz.fooddelivery.api.openapi.controller.EstadoControllerOpenApi;
import com.danielqueiroz.fooddelivery.domain.model.Estado;
import com.danielqueiroz.fooddelivery.domain.service.EstadoService;

@RestController
@RequestMapping(value = "/estados",  produces = MediaType.APPLICATION_JSON_VALUE)
public class EstadoController implements EstadoControllerOpenApi {

	@Autowired
	private EstadoService estadoService;
	
	@Autowired
	private EstadoDTOAssembler estadoDTOAssembler;

	@Autowired
	private EstadoInputDisassembler estadoInputDisassembler;  
	

	@Override
	@GetMapping
	public List<EstadoDTO> buscarTodos() {
		List<Estado> todosEstados = estadoService.buscarTodos();
	    
	    return estadoDTOAssembler.toCollectionModel(todosEstados);
	}

	@Override
	@GetMapping("/{id}")
	public EstadoDTO buscarPorId(@PathVariable Long id) {
		Estado estado = estadoService.buscarPorId(id);
		
		return estadoDTOAssembler.toModel(estado);
	}

	@Override
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public EstadoDTO salvar(@RequestBody @Valid EstadoInput estadoInput) {
		Estado estado = estadoInputDisassembler.toDomainObject(estadoInput);
	    
	    return estadoDTOAssembler.toModel(estadoService.salvar(estado));
	}

	@Override
	@PutMapping("/{id}")
	public EstadoDTO atualizar(@RequestBody @Valid EstadoInput estadoInput, @PathVariable Long id) {
		Estado estadoRetornado = estadoService.buscarPorId(id);

	    estadoInputDisassembler.copyToDomainObject(estadoInput, estadoRetornado);
	    
	    return estadoDTOAssembler.toModel(estadoService.salvar(estadoRetornado));
	}

	@Override
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Estado> deletar(@PathVariable Long id) {
		estadoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}
