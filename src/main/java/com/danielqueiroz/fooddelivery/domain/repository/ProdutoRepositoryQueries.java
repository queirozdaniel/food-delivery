package com.danielqueiroz.fooddelivery.domain.repository;

import com.danielqueiroz.fooddelivery.domain.model.FotoProduto;

public interface ProdutoRepositoryQueries {

	FotoProduto save(FotoProduto foto);
	void delete(FotoProduto foto);
	
}
