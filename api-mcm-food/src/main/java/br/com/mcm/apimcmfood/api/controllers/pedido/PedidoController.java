package br.com.mcm.apimcmfood.api.controllers.pedido;

import br.com.mcm.apimcmfood.api.model.pedido.PedidoListResponse;
import br.com.mcm.apimcmfood.api.model.pedido.PedidoRequest;
import br.com.mcm.apimcmfood.api.model.pedido.PedidoResponse;
import br.com.mcm.apimcmfood.api.model.pedido.mapper.PedidoListResponseMapper;
import br.com.mcm.apimcmfood.api.model.pedido.mapper.PedidoRequestMapper;
import br.com.mcm.apimcmfood.api.model.pedido.mapper.PedidoResponseMapper;
import br.com.mcm.apimcmfood.domain.service.pedido.EmissaoPedidoService;
import br.com.mcm.apimcmfood.domain.entity.Pedido;
import br.com.mcm.apimcmfood.infrastructure.repository.filter.PedidoFilter;
import br.com.mcm.apimcmfood.infrastructure.repository.spec.PedidoSpecification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private PedidoRequestMapper pedidoRequestMapper;
    private PedidoResponseMapper pedidoResponseMapper;
    private PedidoListResponseMapper pedidoListResponseMapper;
    private EmissaoPedidoService pedidoService;

    public PedidoController(
            final PedidoRequestMapper pedidoRequestMapper,
            final EmissaoPedidoService pedidoService,
            final PedidoResponseMapper pedidoResponseMapper,
            final PedidoListResponseMapper pedidoListResponseMapper
    ) {
        this.pedidoRequestMapper = Objects.requireNonNull(pedidoRequestMapper);
        this.pedidoService = Objects.requireNonNull(pedidoService);
        this.pedidoResponseMapper = Objects.requireNonNull(pedidoResponseMapper);
        this.pedidoListResponseMapper = Objects.requireNonNull(pedidoListResponseMapper);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse adicionar(final @RequestBody @Valid PedidoRequest pedidoRequest) {
        Pedido pedido = pedidoRequestMapper.toDomain(pedidoRequest);
        return pedidoResponseMapper.toResponse(pedidoService.salvar(pedido));
    }

    @GetMapping
    public List<PedidoListResponse> pesquisar(PedidoFilter filtro) {
        List<Pedido> todosPedidos = pedidoService.pesquisar(filtro);
        return pedidoListResponseMapper.toCollectionResponse(todosPedidos);
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<PedidoResponse> buscar(final @PathVariable("codigo") String codigo) {
        return ResponseEntity.ok(pedidoResponseMapper.toResponse(pedidoService.buscar(codigo)));
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<PedidoResponse> atualizar(
            final @PathVariable("codigo") String codigo,
            final @RequestBody PedidoRequest pedidoRequest
    ) {
        Pedido pedido = pedidoService.buscar(codigo);
        pedidoRequestMapper.copyToDomainObject(pedidoRequest, pedido);
        return ResponseEntity.ok(pedidoResponseMapper.toResponse(pedidoService.salvar(pedido)));
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<?> remover(final @PathVariable("codigo") String codigo) {
        Pedido pedido = pedidoService.buscar(codigo);
        pedidoService.remover(pedido.getId());
        return ResponseEntity.noContent().build();
    }
}
