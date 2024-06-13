package br.com.mcm.apimcmfood.api.controllers.restaurante;

import br.com.mcm.apimcmfood.api.model.produto.FotoProdutoRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.nio.file.Path;
import java.util.UUID;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void atualizarFoto(
            @PathVariable("restauranteId") Long restauranteId,
            @PathVariable("produtoId") Long produtoId,
            @Valid FotoProdutoRequest fotoProdutoRequest
    ) {
        var nomeArquivo = UUID.randomUUID().toString()
                + "_" + fotoProdutoRequest.getArquivo().getOriginalFilename();
        var arquivoFoto = Path.of("C:/workspace/arquivos/fotos", nomeArquivo);
        try {
            fotoProdutoRequest.getArquivo().transferTo(arquivoFoto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
