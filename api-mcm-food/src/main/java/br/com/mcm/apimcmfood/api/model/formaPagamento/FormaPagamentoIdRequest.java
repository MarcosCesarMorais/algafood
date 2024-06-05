package br.com.mcm.apimcmfood.api.model.formaPagamento;

import javax.validation.constraints.NotNull;

public class FormaPagamentoIdRequest {
    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
