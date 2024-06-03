package br.com.mcm.apimcmfood.api.model.usuario;

import javax.validation.constraints.NotBlank;

public class SenhaRequest {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    private String novaSenha;

    public @NotBlank String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(@NotBlank String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }

    public @NotBlank String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(@NotBlank String novaSenha) {
        this.novaSenha = novaSenha;
    }
}
