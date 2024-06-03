package br.com.mcm.apimcmfood.api.model.restaurante;

import br.com.mcm.apimcmfood.api.model.endereco.EnderecoRequest;
import br.com.mcm.apimcmfood.api.model.cozinha.CozinhaIdRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

public class RestauranteRequest {
    @NotBlank
    private String nome;

    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdRequest cozinha;
    @Valid
    @NotNull
    private EnderecoRequest endereco;

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

    public CozinhaIdRequest getCozinha() {
        return cozinha;
    }

    public void setCozinha(CozinhaIdRequest cozinha) {
        this.cozinha = cozinha;
    }

    public EnderecoRequest getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoRequest endereco) {
        this.endereco = endereco;
    }
}
