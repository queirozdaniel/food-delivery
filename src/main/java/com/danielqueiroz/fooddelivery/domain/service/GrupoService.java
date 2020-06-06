package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeEmUsoException;
import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.model.Grupo;
import com.danielqueiroz.fooddelivery.domain.model.Permissao;
import com.danielqueiroz.fooddelivery.domain.repository.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository grupoRepository;
	
	@Autowired
	private PermissaoService permissaoService;
	
	private static final String MSG_GRUPO_NAO_ENCONTRADA = "Grupo com código %d não encontrado";
	
	public List<Grupo> buscarTodos(){
		return grupoRepository.findAll();
	}
	
	public Grupo buscarPorId(Long id) {
		return grupoRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format(MSG_GRUPO_NAO_ENCONTRADA, id)));
	}
	
	@Transactional
	public Grupo salvar(Grupo grupo) {
		return grupoRepository.save(grupo);
	}

	@Transactional
	public void deletar(Long id) {
		try {
			grupoRepository.deleteById(id);
			grupoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(String.format(MSG_GRUPO_NAO_ENCONTRADA, id));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(String.format("Grupo com código %d está em uso", id));
		}
	}
	
	@Transactional
	public void desassociarPermissao(Long grupoId, Long permissaoId) {
	    Grupo grupo = buscarPorId(grupoId);
	    Permissao permissao = permissaoService.buscarPorId(permissaoId);
	    
	    grupo.removerPermissao(permissao);
	}

	@Transactional
	public void associarPermissao(Long grupoId, Long permissaoId) {
	    Grupo grupo = buscarPorId(grupoId);
	    Permissao permissao = permissaoService.buscarPorId(permissaoId);
	    
	    grupo.adicionarPermissao(permissao);
	} 

}
