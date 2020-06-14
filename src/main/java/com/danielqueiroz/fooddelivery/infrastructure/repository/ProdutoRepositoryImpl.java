package com.danielqueiroz.fooddelivery.infrastructure.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.danielqueiroz.fooddelivery.domain.model.FotoProduto;
import com.danielqueiroz.fooddelivery.domain.repository.ProdutoRepositoryQueries;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepositoryQueries {

	@Autowired
	private EntityManager manager;

	@Transactional
	@Override
	public FotoProduto save(FotoProduto foto) {
		return manager.merge(foto);
	}

	@Transactional
	@Override
	public void delete(FotoProduto foto) {
		manager.remove(foto);
	}
	
	
}
