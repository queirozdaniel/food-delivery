package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import javax.validation.Valid;

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

import com.danielqueiroz.fooddelivery.api.model.CidadeDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.CidadeDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.CidadeInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.input.CidadeInput;
import com.danielqueiroz.fooddelivery.domain.model.Cidade;
import com.danielqueiroz.fooddelivery.domain.service.CidadeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Cidades")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

	@Autowired
	private CidadeService cidadeService;
	
	@Autowired
	private CidadeDTOAssembler cidadeDTOAssembler;

	@Autowired
	private CidadeInputDisassembler cidadeInputDisassembler;    
	

	@ApiOperation("Lista todas as cidades")
	@GetMapping
	public List<CidadeDTO> buscarTodos() {
		List<Cidade> cidades = cidadeService.buscarTodos();
		
		return cidadeDTOAssembler.toCollectionModel(cidades);
	}

	@ApiOperation("Busca uma cidade por ID")
	@GetMapping("/{id}")
	public CidadeDTO buscarPorId(@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id) {
		Cidade cidade = cidadeService.buscarPorId(id);
	    
	    return cidadeDTOAssembler.toModel(cidade);
	}

	@ApiOperation("Cria uma nova cidade")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CidadeDTO salvar(@ApiParam(name = "Corpo",value = "Representação de uma cidade")
								@RequestBody @Valid CidadeInput cidadeInput) {
		
		Cidade cidade = cidadeInputDisassembler.toDomainObject(cidadeInput);
        
        return cidadeDTOAssembler.toModel(cidadeService.salvar(cidade));
	}

	@ApiOperation("Atualiza uma cidade por ID")
	@PutMapping("/{id}")
	public CidadeDTO atualizar(@ApiParam(name = "corpo",value = "Representação de uma cidade com dados atualizados") 
										@RequestBody @Valid CidadeInput cidadeInput,
										@ApiParam(value = "ID de uma cidade", example = "1") @PathVariable Long id) {
		Cidade cidadeRetornada = cidadeService.buscarPorId(id);
        
        cidadeInputDisassembler.copyToDomainObject(cidadeInput, cidadeRetornada);
        
        return cidadeDTOAssembler.toModel(cidadeService.salvar(cidadeRetornada));
	}

	@ApiOperation("Deleta uma cidade por ID")
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Cidade> deletar(@ApiParam(value = "ID de uma cidade", example = "1")  @PathVariable Long id) {
		cidadeService.deletar(id);
		return ResponseEntity.noContent().build();
	}

}
