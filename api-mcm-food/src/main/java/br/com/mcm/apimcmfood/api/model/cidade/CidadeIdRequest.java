package br.com.mcm.apimcmfood.api.model.cidade;

import javax.validation.constraints.NotNull;

public class CidadeIdRequest {
    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
