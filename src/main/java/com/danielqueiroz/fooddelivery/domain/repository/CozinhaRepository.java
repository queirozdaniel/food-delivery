package com.danielqueiroz.fooddelivery.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.danielqueiroz.fooddelivery.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long>{

	List<Cozinha> findByNomeContaining(String nome);
	Optional<Cozinha> findByNome(String nome);
	
}
