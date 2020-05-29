package com.danielqueiroz.fooddelivery.domain.repository;

import org.springframework.stereotype.Repository;

import com.danielqueiroz.fooddelivery.domain.model.Pedido;

@Repository
public interface PedidoRepository extends CustomJpaRepository<Pedido, Long>{

}
