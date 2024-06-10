package br.com.mcm.apimcmfood.api.model.cidade;

import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteResponse;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class CidadeListResponse {

    private Long id;

    private String nome;

    @JsonIgnore
    private RestauranteResponse restauranteResponse;

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
