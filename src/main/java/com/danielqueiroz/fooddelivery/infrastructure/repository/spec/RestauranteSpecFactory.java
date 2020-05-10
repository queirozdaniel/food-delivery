package com.danielqueiroz.fooddelivery.infrastructure.repository.spec;

import java.math.BigDecimal;

import org.springframework.data.jpa.domain.Specification;

import com.danielqueiroz.fooddelivery.domain.model.Restaurante;

public class RestauranteSpecFactory {

	public static Specification<Restaurante> comFreteGratis(){
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("taxaFrete"), BigDecimal.ZERO);
	}
	public static Specification<Restaurante> comNomeSemelhante(String nome){
		return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("nome"), "%"+ nome + "%");
	}
	
	
}
