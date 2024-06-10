package br.com.mcm.apimcmfood.api.model.cidade;

import br.com.mcm.apimcmfood.api.model.estado.EstadoIdRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CidadeRequest {

    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIdRequest estado;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EstadoIdRequest getEstado() {
        return estado;
    }

    public void setEstado(EstadoIdRequest estado) {
        this.estado = estado;
    }
}
