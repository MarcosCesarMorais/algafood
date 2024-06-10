package br.com.mcm.apimcmfood.api.model.cidade;

import br.com.mcm.apimcmfood.api.model.estado.EstadoResponse;

public class CidadeResponse {

    private Long id;

    private String nome;

    private EstadoResponse estado;

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

    public EstadoResponse getEstado() {
        return estado;
    }

    public void setEstado(EstadoResponse estado) {
        this.estado = estado;
    }
}
