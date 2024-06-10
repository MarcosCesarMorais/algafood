package br.com.mcm.apimcmfood.api.controllers.pedido;

import br.com.mcm.apimcmfood.api.model.pedido.PedidoListResponse;
import br.com.mcm.apimcmfood.api.model.pedido.PedidoRequest;
import br.com.mcm.apimcmfood.api.model.pedido.PedidoResponse;
import br.com.mcm.apimcmfood.api.model.pedido.mapper.PedidoListResponseMapper;
import br.com.mcm.apimcmfood.api.model.pedido.mapper.PedidoRequestMapper;
import br.com.mcm.apimcmfood.api.model.pedido.mapper.PedidoResponseMapper;
import br.com.mcm.apimcmfood.domain.service.pedido.EmissaoPedidoService;
import br.com.mcm.apimcmfood.domain.entity.Pedido;
import br.com.mcm.apimcmfood.infrastructure.repository.PedidoRepository;
import br.com.mcm.apimcmfood.infrastructure.repository.filter.PedidoFilter;
import br.com.mcm.apimcmfood.utils.data.PageableTranslator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/pedidos")
public class PedidoController {

    private PedidoRequestMapper pedidoRequestMapper;
    private PedidoResponseMapper pedidoResponseMapper;
    private PedidoListResponseMapper pedidoListResponseMapper;
    private EmissaoPedidoService pedidoService;

    private PedidoRepository pedidoRepository;

    public PedidoController(
            final PedidoRequestMapper pedidoRequestMapper,
            final EmissaoPedidoService pedidoService,
            final PedidoResponseMapper pedidoResponseMapper,
            final PedidoListResponseMapper pedidoListResponseMapper,
            final PedidoRepository pedidoRepository
    ) {
        this.pedidoRequestMapper = Objects.requireNonNull(pedidoRequestMapper);
        this.pedidoService = Objects.requireNonNull(pedidoService);
        this.pedidoResponseMapper = Objects.requireNonNull(pedidoResponseMapper);
        this.pedidoListResponseMapper = Objects.requireNonNull(pedidoListResponseMapper);
        this.pedidoRepository = pedidoRepository;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoResponse adicionar(final @RequestBody @Valid PedidoRequest pedidoRequest) {
        Pedido pedido = pedidoRequestMapper.toDomain(pedidoRequest);
        return pedidoResponseMapper.toResponse(pedidoService.salvar(pedido));
    }

    @GetMapping
    public Page<PedidoListResponse> pesquisar(PedidoFilter filtro, @PageableDefault(size = 10) Pageable pageable) {
        pageable = traduzirPageable(pageable);
        Page<Pedido> todosPedidos = pedidoService.pesquisar(filtro, pageable);
        return pedidoListResponseMapper.toPageableToResponse(todosPedidos);
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

    private Pageable traduzirPageable(Pageable apiPageable) {
        var mapeamento = Map.of(
                "codigo", "codigo",
                "subtotal", "subtotal",
                "taxaFrete", "taxaFrete",
                "valorTotal", "valorTotal",
                "dataCriacao", "dataCriacao",
                "restaurante.nome", "restaurante.nome",
                "restaurante.id", "restaurante.id",
                "cliente.id", "cliente.id",
                "cliente.nome", "cliente.nome"
        );
        return PageableTranslator.translate(apiPageable, mapeamento);
    }
}
