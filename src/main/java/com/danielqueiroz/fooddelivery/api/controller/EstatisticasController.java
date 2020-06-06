package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.domain.filter.VendaDiariaFilter;
import com.danielqueiroz.fooddelivery.domain.model.VendaDiaria;
import com.danielqueiroz.fooddelivery.domain.service.VendaQueryService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@GetMapping("/vendas-diarias")
	public List<VendaDiaria> consultarVendas(VendaDiariaFilter filtro,@RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}
	
	
}
