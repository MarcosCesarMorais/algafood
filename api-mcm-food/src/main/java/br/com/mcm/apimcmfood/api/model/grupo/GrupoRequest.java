package br.com.mcm.apimcmfood.api.model.grupo;

import br.com.mcm.apimcmfood.api.model.permissao.PermissaoResponse;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class GrupoRequest {

    @NotBlank
    private String nome;

    private List<PermissaoResponse> permissoes;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<PermissaoResponse> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<PermissaoResponse> permissoes) {
        this.permissoes = permissoes;
    }
}
