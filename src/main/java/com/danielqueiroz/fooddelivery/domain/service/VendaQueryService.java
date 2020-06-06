package com.danielqueiroz.fooddelivery.domain.service;

import java.util.List;

import com.danielqueiroz.fooddelivery.domain.filter.VendaDiariaFilter;
import com.danielqueiroz.fooddelivery.domain.model.VendaDiaria;

public interface VendaQueryService {

	List<VendaDiaria> consultarVendasDiarias(VendaDiariaFilter filtro);
	
}
