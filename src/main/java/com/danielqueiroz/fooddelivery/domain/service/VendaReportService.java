package com.danielqueiroz.fooddelivery.domain.service;

import com.danielqueiroz.fooddelivery.domain.filter.VendaDiariaFilter;

public interface VendaReportService {

	byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
}
