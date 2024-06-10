package br.com.mcm.apimcmfood.api.model.formaPagamento;

import javax.validation.constraints.NotBlank;

public class FormaPagamentoRequest {
    @NotBlank
    private String descricao;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
