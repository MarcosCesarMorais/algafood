package br.com.mcm.apimcmfood.api.model.cozinha;

import javax.validation.constraints.NotBlank;

public class CozinhaRequest {

    @NotBlank
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
