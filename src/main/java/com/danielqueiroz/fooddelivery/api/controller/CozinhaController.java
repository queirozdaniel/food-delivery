package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

import com.danielqueiroz.fooddelivery.api.model.CozinhaDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.CozinhaDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.CozinhaInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.input.CozinhaInput;
import com.danielqueiroz.fooddelivery.domain.model.Cozinha;
import com.danielqueiroz.fooddelivery.domain.service.CozinhaService;

@RestController
@RequestMapping(value = "/cozinhas", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
public class CozinhaController {

	@Autowired
	private CozinhaService cozinhaService;
	
	@Autowired
	private CozinhaDTOAssembler cozinhaDTOAssembler;

	@Autowired
	private CozinhaInputDisassembler cozinhaInputDisassembler;  
	

	@GetMapping
	public Page<CozinhaDTO> listarTodas(@PageableDefault(size = 10) Pageable pageable) {
		Page<Cozinha> cozinhasPages = cozinhaService.buscarTodos(pageable);
		
		List<CozinhaDTO> cozinhasDTO = cozinhaDTOAssembler.toCollectionModel(cozinhasPages.getContent());
		
		Page<CozinhaDTO> cozinhasPagesDTO = new PageImpl<>(cozinhasDTO, pageable, cozinhasPages.getTotalElements());
		
		return cozinhasPagesDTO;
	}

	@GetMapping("/{id}")
	public CozinhaDTO buscarPorId(@PathVariable("id") Long id) {
		Cozinha cozinha = cozinhaService.buscarPorId(id);
		
		return cozinhaDTOAssembler.toModel(cozinha);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CozinhaDTO adicionar(@RequestBody @Valid CozinhaInput cozinhaInput) {
		Cozinha cozinha = cozinhaInputDisassembler.toDomainObject(cozinhaInput);
		
		return cozinhaDTOAssembler.toModel(cozinhaService.salvar(cozinha));
	}

	@PutMapping("/{id}")
	public CozinhaDTO atualizar(@RequestBody CozinhaInput cozinhaInput, @PathVariable Long id) {
		Cozinha cozinhaRetornada = cozinhaService.buscarPorId(id);

		cozinhaInputDisassembler.copyToDomainObject(cozinhaInput, cozinhaRetornada);
		
		return cozinhaDTOAssembler.toModel(cozinhaService.salvar(cozinhaRetornada));
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
