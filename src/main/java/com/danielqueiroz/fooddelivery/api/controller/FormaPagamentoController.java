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

import com.danielqueiroz.fooddelivery.api.model.FormaPagamentoDTO;
import com.danielqueiroz.fooddelivery.api.model.assembler.FormaPagamentoDTOAssembler;
import com.danielqueiroz.fooddelivery.api.model.assembler.FormaPagamentoInputDisassembler;
import com.danielqueiroz.fooddelivery.api.model.input.FormaPagamentoInput;
import com.danielqueiroz.fooddelivery.domain.model.FormaPagamento;
import com.danielqueiroz.fooddelivery.domain.service.FormaPagamentoService;

@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {
    
    @Autowired
    private FormaPagamentoService formaPagamentoService;
    
    @Autowired
    private FormaPagamentoDTOAssembler formaPagamentoDTOAssembler;
    
    @Autowired
    private FormaPagamentoInputDisassembler formaPagamentoInputDisassembler;
    
    @GetMapping
    public List<FormaPagamentoDTO> listar() {
        List<FormaPagamento> todasFormasPagamentos = formaPagamentoService.buscarTodos();
        
        return formaPagamentoDTOAssembler.toCollectionModel(todasFormasPagamentos);
    }
    
    @GetMapping("/{id}")
    public FormaPagamentoDTO buscar(@PathVariable Long id) {
        FormaPagamento formaPagamento = formaPagamentoService.buscarPorId(id);
        
        return formaPagamentoDTOAssembler.toModel(formaPagamento);
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO adicionar(@RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamento = formaPagamentoInputDisassembler.toDomainObject(formaPagamentoInput);
        
        return formaPagamentoDTOAssembler.toModel(formaPagamentoService.salvar(formaPagamento));
    }
    
    @PutMapping("/{id}")
    public FormaPagamentoDTO atualizar(@PathVariable Long id,
            @RequestBody @Valid FormaPagamentoInput formaPagamentoInput) {
        FormaPagamento formaPagamentoAtual = formaPagamentoService.buscarPorId(id);
        
        formaPagamentoInputDisassembler.copyToDomainObject(formaPagamentoInput, formaPagamentoAtual);
        
        return formaPagamentoDTOAssembler.toModel(formaPagamentoService.salvar(formaPagamentoAtual));
    }
    
    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remover(@PathVariable Long formaPagamentoId) {
        formaPagamentoService.deletar(formaPagamentoId);	
    }  
	
}
