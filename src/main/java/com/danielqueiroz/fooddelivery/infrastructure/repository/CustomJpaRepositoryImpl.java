package com.danielqueiroz.fooddelivery.infrastructure.repository;

import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import com.danielqueiroz.fooddelivery.domain.repository.CustomJpaRepository;
import com.sun.xml.bind.v2.model.core.ID;

public class CustomJpaRepositoryImpl<T, D> extends SimpleJpaRepository<T, ID> implements CustomJpaRepository<T, ID>{

	private EntityManager manager;
	
	public CustomJpaRepositoryImpl(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		
		this.manager = entityManager;
	}

	@Override
	public Optional<T> buscarPrimeiro() {
		var jpql = "from " + getDomainClass().getName();
		T entity = manager.createQuery(jpql, getDomainClass()).setMaxResults(1).getSingleResult();
		
		return Optional.ofNullable(entity);
	}

	@Override
	public void detach(T entity) {
		manager.detach(entity);
	}

}
