package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.filtros.FiltroVendaDiaria;
import br.com.mcm.apimcmfood.domain.projecoes.VendasDiaria;

import java.util.List;

public interface VendasQueryService {

    List<VendasDiaria> consultarVendasDiarias(FiltroVendaDiaria filtro, String timeOffset);
}
