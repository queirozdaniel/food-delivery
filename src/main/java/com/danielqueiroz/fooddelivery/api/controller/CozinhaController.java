package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.domain.model.Cozinha;
import com.danielqueiroz.fooddelivery.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class CozinhaController {

	@Autowired
	private CozinhaService cozinhaService;

	@GetMapping
	public List<Cozinha> listarTodas() {
		return cozinhaService.buscarTodos();
	}

	@GetMapping("/{id}")
	public Cozinha buscarPorId(@PathVariable("id") Long id) {
		return cozinhaService.buscarPorId(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return cozinhaService.salvar(cozinha);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@RequestBody Cozinha cozinha, @PathVariable Long id) {
		Cozinha cozinhaRetornada = cozinhaService.buscarPorId(id);

		BeanUtils.copyProperties(cozinha, cozinhaRetornada, "id");
		cozinhaService.salvar(cozinhaRetornada);

		return ResponseEntity.ok(cozinhaRetornada);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Cozinha> deletar(@PathVariable Long id) {
		cozinhaService.deletar(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/nome")
	public ResponseEntity<?> pesquisaEmNome(String nome) {
		return ResponseEntity.ok(cozinhaService.buscarPorNome(nome));
	}

	@GetMapping("/todas")
	public List<Cozinha> listarTodasQueContemEmNome(@RequestParam String nome) {
		return cozinhaService.buscarTodasContemNoNome(nome);
	}

}
