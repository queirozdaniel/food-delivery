package com.danielqueiroz.fooddelivery.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.danielqueiroz.fooddelivery.domain.filter.VendaDiariaFilter;
import com.danielqueiroz.fooddelivery.domain.model.VendaDiaria;
import com.danielqueiroz.fooddelivery.domain.service.VendaQueryService;
import com.danielqueiroz.fooddelivery.domain.service.VendaReportService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasController {

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@Autowired
	private VendaReportService vendaReportService;
	
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiaria> consultarVendas(VendaDiariaFilter filtro,@RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}
	
	@GetMapping(value = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasPdf(VendaDiariaFilter filtro,@RequestParam(required = false, defaultValue = "+00:00") String timeOffset){
		
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);
		
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");
		
		return ResponseEntity.ok().contentType(MediaType.APPLICATION_PDF).headers(headers).body(bytesPdf);
	}
	
}