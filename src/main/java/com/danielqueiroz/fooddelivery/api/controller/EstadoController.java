package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

import com.danielqueiroz.fooddelivery.domain.model.Estado;
import com.danielqueiroz.fooddelivery.domain.service.EstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {

	@Autowired
	private EstadoService estadoService;

	@GetMapping
	public List<Estado> buscarTodos() {
		return estadoService.buscarTodos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Estado> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(estadoService.buscarPorId(id));
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Estado salvar(@RequestBody @Valid Estado estado) {
		return estadoService.salvar(estado);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Estado> atualizar(@RequestBody @Valid Estado estado, @PathVariable Long id) {
		Estado estadoRetornado = estadoService.buscarPorId(id);

		BeanUtils.copyProperties(estado, estadoRetornado, "id");
		estadoService.salvar(estadoRetornado);

		return ResponseEntity.ok(estadoRetornado);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Estado> deletar(@PathVariable Long id) {
		estadoService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}
