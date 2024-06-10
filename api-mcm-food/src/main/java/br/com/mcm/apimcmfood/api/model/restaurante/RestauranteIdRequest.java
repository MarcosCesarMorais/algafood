package br.com.mcm.apimcmfood.api.model.restaurante;

import javax.validation.constraints.NotNull;

public class RestauranteIdRequest {
    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
