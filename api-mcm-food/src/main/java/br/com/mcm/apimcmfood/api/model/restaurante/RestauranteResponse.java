package br.com.mcm.apimcmfood.api.model.restaurante;

import br.com.mcm.apimcmfood.api.model.cozinha.CozinhaResponse;

import java.math.BigDecimal;

public class RestauranteResponse {
    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaResponse cozinha;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getTaxaFrete() {
        return taxaFrete;
    }

    public void setTaxaFrete(BigDecimal taxaFrete) {
        this.taxaFrete = taxaFrete;
    }

    public CozinhaResponse getCozinha() {
        return cozinha;
    }

    public void setCozinha(CozinhaResponse cozinha) {
        this.cozinha = cozinha;
    }
}
