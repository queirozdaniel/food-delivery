package com.danielqueiroz.fooddelivery.domain.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VendaDiaria {

	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturamento;
	
}
