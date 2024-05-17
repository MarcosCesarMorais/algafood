package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.application.service.PedidoService;
import br.com.mcm.apimcmfood.domain.entity.Pedido;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pedido adicionar(@RequestBody Pedido pedido){
        return pedidoService.adicionar(pedido);
    }

    @GetMapping
    public List<Pedido> listar( ) {
        return pedidoService.listar();
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
    ){
        return ResponseEntity.ok(pedidoService.atualizarParcial(id, campos));
    }

    @DeleteMapping("/{pedidoId}")
    public ResponseEntity<Pedido> remover(@PathVariable("pedidoId") Long id) {
        this.pedidoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
