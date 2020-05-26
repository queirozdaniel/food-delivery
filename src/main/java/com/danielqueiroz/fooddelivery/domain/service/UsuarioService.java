package com.danielqueiroz.fooddelivery.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.danielqueiroz.fooddelivery.domain.exception.EntidadeNaoEncontradaException;
import com.danielqueiroz.fooddelivery.domain.exception.NegocioException;
import com.danielqueiroz.fooddelivery.domain.model.Usuario;
import com.danielqueiroz.fooddelivery.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public Usuario salvar(Usuario usuario) {
    	usuarioRepository.detach(usuario);
    	
    	Optional<Usuario> usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
    	
    	if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
			throw new NegocioException(String.format("já existe um usuário cadastrado com o email: %s", usuario.getEmail()));
		}
    	
        return usuarioRepository.save(usuario);
    }
    
    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarPorId(usuarioId);
        
        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }
        
        usuario.setSenha(novaSenha);
    }
	
    public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException(String.format("Usuário com id %d não foi encontrado", id)));
	}
	
}
