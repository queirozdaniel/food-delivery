package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.exception.EstadoNaoEncontradoException;
import com.danielqueiroz.fooddelivery.domain.exception.NegocioException;
import com.danielqueiroz.fooddelivery.domain.model.Cidade;
import com.danielqueiroz.fooddelivery.domain.service.CidadeService;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;

	@GetMapping
	public List<Cidade> buscarTodos() {
		return cidadeService.buscarTodos();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Cidade> buscarPorId(@PathVariable Long id) {
		try {
			return ResponseEntity.ok(cidadeService.buscarPorId(id));

		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cidade salvar(@RequestBody @Valid Cidade cidade) {
		try {
			return cidadeService.salvar(cidade);
		} catch (EstadoNaoEncontradoException e) {
			throw new NegocioException(e.getMessage(), e);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<Cidade> atualizar(@RequestBody @Valid Cidade cidade, @PathVariable Long id) {

		Cidade cidadeRetornada = cidadeService.buscarPorId(id);

		BeanUtils.copyProperties(cidade, cidadeRetornada, "id");
		cidadeService.salvar(cidadeRetornada);

		return ResponseEntity.ok(cidadeRetornada);

	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Cidade> deletar(@PathVariable Long id) {

		try {
			cidadeService.deletar(id);
			return ResponseEntity.noContent().build();

		} catch (DataIntegrityViolationException ex) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();

		} catch (EntidadeNaoEncontradaException ex) {
			return ResponseEntity.notFound().build();
		}
	}

}
