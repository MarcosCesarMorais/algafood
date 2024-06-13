package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.filtros.FiltroVendaDiaria;

public interface VendaReportService {

    byte[] emitirVendasDiarias(FiltroVendaDiaria filtro, String timeOffset);
}
