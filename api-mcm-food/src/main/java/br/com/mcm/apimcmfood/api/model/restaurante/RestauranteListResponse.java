package br.com.mcm.apimcmfood.api.model.restaurante;

import br.com.mcm.apimcmfood.api.model.cozinha.CozinhaListResponse;
import br.com.mcm.apimcmfood.api.model.cozinha.CozinhaResponse;
import br.com.mcm.apimcmfood.api.model.endereco.EnderecoListResponse;

import java.math.BigDecimal;

public class RestauranteListResponse {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private String cozinha;
    private EnderecoListResponse endereco;

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

    public String getCozinha() {
        return cozinha;
    }

    public void setCozinha(String cozinha) {
        this.cozinha = cozinha;
    }

    public EnderecoListResponse getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoListResponse endereco) {
        this.endereco = endereco;
    }
}
