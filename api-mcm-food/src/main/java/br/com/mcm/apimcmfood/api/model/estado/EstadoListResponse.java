package br.com.mcm.apimcmfood.api.model.estado;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class EstadoListResponse {

    @NotNull
    private Long id;
    @NotBlank
    private String nome;

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
}
