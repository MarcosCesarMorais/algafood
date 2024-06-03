package br.com.mcm.apimcmfood.api.model.grupo;

import br.com.mcm.apimcmfood.api.model.permissao.PermissaoResponse;

import java.util.List;

public class GrupoResponse {

    private Long id;

    private String nome;

    private List<PermissaoResponse> permissoes;

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

    public List<PermissaoResponse> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<PermissaoResponse> permissoes) {
        this.permissoes = permissoes;
    }
}
