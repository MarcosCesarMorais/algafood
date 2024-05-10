package br.com.mcm.apimcmfood.infrastructure.repository.custom;

import br.com.mcm.apimcmfood.domain.entity.Restaurante;

import java.math.BigDecimal;
import java.util.List;

public interface RestauranteRepositoryQueries {

    List<Restaurante> find (
            String nome,
            BigDecimal taxaFreteInicial,
            BigDecimal taxaFreteFinal
    );
    List<Restaurante> findComFreteGratis (
            String nome
    );
}
