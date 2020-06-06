package com.danielqueiroz.fooddelivery.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Permissao;
import com.danielqueiroz.fooddelivery.domain.repository.PermissaoRepository;

@Service
public class PermissaoService {
	
	 @Autowired
	 private PermissaoRepository permissaoRepository;
	    
    public Permissao buscarPorId(Long id) {
        return permissaoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de permissão com código %d", id)));
    }

}
