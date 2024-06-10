package br.com.mcm.apimcmfood.api.model.usuario;

import javax.validation.constraints.NotBlank;

public class UsuarioComSenhaRequest extends UsuarioRequest {

    @NotBlank
    private String senha;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
