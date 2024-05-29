package br.com.mcm.apimcmfood.api.model.cozinha;

import javax.validation.constraints.NotNull;

public class CozinhaIdRequest {
    @NotNull
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
