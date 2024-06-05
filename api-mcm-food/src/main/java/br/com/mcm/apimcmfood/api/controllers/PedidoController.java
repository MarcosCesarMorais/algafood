package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.api.model.pedido.PedidoRequest;
import br.com.mcm.apimcmfood.api.model.pedido.PedidoResponse;
import br.com.mcm.apimcmfood.api.model.pedido.mapper.PedidoRequestMapper;
import br.com.mcm.apimcmfood.api.model.pedido.mapper.PedidoResponseMapper;
import br.com.mcm.apimcmfood.domain.service.PedidoService;
import br.com.mcm.apimcmfood.domain.entity.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private PedidoRequestMapper pedidoRequestMapper;
    private PedidoResponseMapper pedidoResponseMapper;
    private PedidoService pedidoService;

    public PedidoController(
            final PedidoRequestMapper pedidoRequestMapper,
            final PedidoService pedidoService,
            final PedidoResponseMapper pedidoResponseMapper
    ) {
        this.pedidoRequestMapper = pedidoRequestMapper;
        this.pedidoService = pedidoService;
        this.pedidoResponseMapper = pedidoResponseMapper;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse adicionar(final @RequestBody @Valid PedidoRequest pedidoRequest) {
        Pedido pedido = pedidoRequestMapper.toDomain(pedidoRequest);
        return pedidoResponseMapper.toResponse(pedidoService.adicionar(pedido));
    }

    @GetMapping
    public List<PedidoResponse> listar() {
        return pedidoResponseMapper.toCollectionModel(pedidoService.listar());
    }

    @GetMapping("/{pedidoId}")
    public ResponseEntity<Pedido> buscar(@PathVariable("pedidoId") Long id) {
        return ResponseEntity.ok(pedidoService.buscar(id));
    }

    @PutMapping("/{pedidoId}")
    public ResponseEntity<Pedido> atualizar(
            final @PathVariable("pedidoId") Long id,
            final @RequestBody Pedido pedido
    ) {
        return ResponseEntity.ok(pedidoService.atualizar(id, pedido));
    }

    @PatchMapping("/{pedidoId}")
    public ResponseEntity<Pedido> atualizarParcial(
            final @PathVariable("pedidoId") Long id,
            final @RequestBody Map<String, Object> campos
    ) {
        return ResponseEntity.ok(pedidoService.atualizarParcial(id, campos));
    }

    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<Pedido> remover(@PathVariable("pedidoId") Long id) {
        this.pedidoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
