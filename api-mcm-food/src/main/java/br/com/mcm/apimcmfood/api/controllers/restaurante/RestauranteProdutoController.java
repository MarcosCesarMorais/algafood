package br.com.mcm.apimcmfood.api.controllers.restaurante;

import br.com.mcm.apimcmfood.api.model.produto.ProdutoListResponse;
import br.com.mcm.apimcmfood.api.model.produto.ProdutoResponse;
import br.com.mcm.apimcmfood.api.model.produto.mapper.ProdutoResponseMapper;
import br.com.mcm.apimcmfood.domain.entity.Produto;
import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import br.com.mcm.apimcmfood.domain.service.ProdutoService;
import br.com.mcm.apimcmfood.domain.service.RestauranteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private RestauranteService restauranteService;
    private ProdutoService produtoService;
    private ProdutoResponseMapper produtoResponseMapper;

    public RestauranteProdutoController(
            final RestauranteService restauranteService,
            final ProdutoService produtoService,
            final ProdutoResponseMapper produtoResponseMapper
    ) {
        this.restauranteService = Objects.requireNonNull(restauranteService);
        this.produtoService = Objects.requireNonNull(produtoService);
        this.produtoResponseMapper = Objects.requireNonNull(produtoResponseMapper);
    }

    @GetMapping
    public List<ProdutoListResponse> listar(@PathVariable Long restauranteId,
                                            @RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);

        List<Produto> todosProdutos = null;

        if (incluirInativos) {
            todosProdutos = produtoService.findTodosByRestaurante(restaurante);
        } else {
            todosProdutos = produtoService.findAtivosByRestaurante(restaurante);
        }

        return produtoResponseMapper.toCollectionResponse(todosProdutos);
    }
}
