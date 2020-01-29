package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.domain.model.Cozinha;
import com.danielqueiroz.fooddelivery.domain.repository.CozinhaRepository;

@RestController
@RequestMapping(value = "/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CozinhaController {

	@Autowired
	private CozinhaRepository cozinhaRepository;
	
	@GetMapping
	public List<Cozinha> listarTodas(){
		return cozinhaRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cozinha> buscarPorId(@PathVariable("id") Long id) {
		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
		
		if (cozinha.isPresent()) {
			return ResponseEntity.ok(cozinha.get());			
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cozinha adicionar(@RequestBody Cozinha cozinha) {
		return cozinhaRepository.save(cozinha);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Cozinha> atualizar(@RequestBody Cozinha cozinha, @PathVariable Long id) {
		Optional<Cozinha> cozinhaRetornada = cozinhaRepository.findById(id);
		
		if (cozinhaRetornada.isPresent()) {
			BeanUtils.copyProperties(cozinha, cozinhaRetornada.get(), "id");
			cozinhaRepository.save(cozinhaRetornada.get());
			
			return ResponseEntity.ok(cozinhaRetornada.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping(value ="/{id}")
	public ResponseEntity<Cozinha> deletar(@PathVariable Long id) {
		
		Optional<Cozinha> cozinha = cozinhaRepository.findById(id);
		try {
			if (cozinha.isPresent()) {
				cozinhaRepository.deleteById(id);
				
				return ResponseEntity.noContent().build();
			}	
		} catch(DataIntegrityViolationException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		
		
		return ResponseEntity.notFound().build();
	}
	
	
}
