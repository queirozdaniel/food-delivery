package com.danielqueiroz.fooddelivery.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.danielqueiroz.fooddelivery.domain.model.FormaPagamento;

public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Long> {

}
