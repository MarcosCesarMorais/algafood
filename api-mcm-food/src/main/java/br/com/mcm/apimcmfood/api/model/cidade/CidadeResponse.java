package br.com.mcm.apimcmfood.api.model.cidade;

import br.com.mcm.apimcmfood.api.model.estado.EstadoResponse;
import br.com.mcm.apimcmfood.domain.entity.Estado;
import br.com.mcm.apimcmfood.domain.exception.groups.Groups;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;

public class CidadeResponse {
    private Long id;
    @NotBlank
    private String nome;
    @Valid
    @NotNull
    @JsonIgnoreProperties(value = "nome", allowGetters = true)
    private EstadoResponse estado;
}
