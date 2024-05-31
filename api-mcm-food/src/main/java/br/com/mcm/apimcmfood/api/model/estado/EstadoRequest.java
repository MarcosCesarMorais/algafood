package br.com.mcm.apimcmfood.api.model.estado;
import javax.validation.constraints.NotBlank;


public class EstadoRequest {

    @NotBlank
    private String nome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
