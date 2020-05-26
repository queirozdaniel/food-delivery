package com.danielqueiroz.fooddelivery.domain.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.danielqueiroz.fooddelivery.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends CustomJpaRepository<Usuario, Long>{

	Optional<Usuario> findByEmail(String email);
	
}
