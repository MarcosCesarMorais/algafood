package br.com.mcm.apimcmfood.api.model.estado;

import javax.validation.constraints.NotNull;

public class EstadoIdRequest {

    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
