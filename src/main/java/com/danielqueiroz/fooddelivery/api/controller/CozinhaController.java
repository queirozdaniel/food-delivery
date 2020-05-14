package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Cozinha;
import com.danielqueiroz.fooddelivery.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CozinhaController {

	@Autowired
	private CozinhaService cozinhaService;
	
	@GetMapping
	public List<Cozinha> listarTodas(){
		return cozinhaService.buscarTodos();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscarPorId(@PathVariable("id") Long id) {
		try {
			return ResponseEntity.ok(cozinhaService.buscarPorId(id));

		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody @Valid Cozinha cozinha) {
		return cozinhaService.salvar(cozinha);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@RequestBody Cozinha cozinha, @PathVariable Long id) {
		try {
			Cozinha cozinhaRetornada = cozinhaService.buscarPorId(id);

			BeanUtils.copyProperties(cozinha, cozinhaRetornada, "id");
			cozinhaService.salvar(cozinhaRetornada);

			return ResponseEntity.ok(cozinhaRetornada);
		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping(value ="/{id}")
	public ResponseEntity<Cozinha> deletar(@PathVariable Long id) {
		
		try {
			cozinhaService.deletar(id);
			return ResponseEntity.noContent().build();
	
		} catch (DataIntegrityViolationException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		
		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/nome")
	public ResponseEntity<?> pesquisaEmNome(String nome){
		try {
			return ResponseEntity.ok(cozinhaService.buscarPorNome(nome));

		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/todas")
	public List<Cozinha> listarTodasQueContemEmNome(@RequestParam String nome){
		return cozinhaService.buscarTodasContemNoNome(nome);
	}
	
}
