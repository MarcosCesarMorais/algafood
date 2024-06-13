package br.com.mcm.apimcmfood.api.model.produto;

import br.com.mcm.apimcmfood.utils.validacao.FileSize;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FotoProdutoRequest {
    @NotNull
    @FileSize(max = "5KB")
    private MultipartFile arquivo;
    @NotBlank
    private String descricao;

    public MultipartFile getArquivo() {
        return arquivo;
    }

    public void setArquivo(MultipartFile arquivo) {
        this.arquivo = arquivo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
