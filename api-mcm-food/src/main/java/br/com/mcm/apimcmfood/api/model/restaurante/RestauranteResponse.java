package br.com.mcm.apimcmfood.api.model.restaurante;

import br.com.mcm.apimcmfood.api.model.cozinha.CozinhaResponse;
import br.com.mcm.apimcmfood.api.model.endereco.EnderecoResponse;

import java.math.BigDecimal;

public class RestauranteResponse {
    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private CozinhaResponse cozinha;
    private Boolean ativo;
    private EnderecoResponse endereco;

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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public EnderecoResponse getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoResponse endereco) {
        this.endereco = endereco;
    }
}
