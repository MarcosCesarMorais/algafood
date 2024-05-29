package br.com.mcm.apimcmfood.api.model.restaurante;

import br.com.mcm.apimcmfood.api.model.cozinha.CozinhaIdRequest;
import br.com.mcm.apimcmfood.api.model.cozinha.CozinhaResponse;
import br.com.mcm.apimcmfood.domain.exception.groups.Groups;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
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
}
