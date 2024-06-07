package br.com.mcm.apimcmfood.api.controllers.pedido;

import br.com.mcm.apimcmfood.domain.service.pedido.AlteracaoStatusPedidoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping(value = "/pedidos/{pedidoId}")
public class AlteracaoStatusPedidoController {

    private AlteracaoStatusPedidoService alteracaoStatusPedidoService;

    public AlteracaoStatusPedidoController(final AlteracaoStatusPedidoService alteracaoStatusPedidoService) {
        this.alteracaoStatusPedidoService = Objects.requireNonNull(alteracaoStatusPedidoService);
    }

    @PutMapping("/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(final @PathVariable("codigo") String codigo) {
        alteracaoStatusPedidoService.confirmar(codigo);
    }
}
