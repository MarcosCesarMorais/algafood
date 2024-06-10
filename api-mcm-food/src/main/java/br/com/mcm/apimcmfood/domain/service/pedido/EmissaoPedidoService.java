package br.com.mcm.apimcmfood.domain.service.pedido;

import br.com.mcm.apimcmfood.domain.entity.*;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.exception.NegocioException;
import br.com.mcm.apimcmfood.domain.service.*;
import br.com.mcm.apimcmfood.infrastructure.repository.PedidoRepository;
import br.com.mcm.apimcmfood.infrastructure.repository.filter.PedidoFilter;
import br.com.mcm.apimcmfood.infrastructure.repository.spec.PedidoSpecification;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class EmissaoPedidoService {

    private static final String MSG_PEDIDO_EM_USO =
            "Pedido de código %d não pode ser removido, pois está em uso";

    private static final String MSG_PEDIDO_NAO_ENCONTRADO =
            "Não existe um cadastro de PedidoRequest com código %d";

    private PedidoRepository pedidoRepository;
    private RestauranteService restauranteService;
    private CidadeService cidadeService;
    private ProdutoService produtoService;
    private FormaPagamentoService formaPagamentoService;
    private UsuarioService usuarioService;

    public EmissaoPedidoService(
            final PedidoRepository pedidoRepository,
            final UsuarioService usuarioService,
            final RestauranteService restauranteService,
            final CidadeService cidadeService,
            final ProdutoService produtoService,
            final FormaPagamentoService formaPagamentoService
    ) {
        this.usuarioService = Objects.requireNonNull(usuarioService);
        this.pedidoRepository = Objects.requireNonNull(pedidoRepository);
        this.restauranteService = Objects.requireNonNull(restauranteService);
        this.cidadeService = Objects.requireNonNull(cidadeService);
        this.produtoService = Objects.requireNonNull(produtoService);
        this.formaPagamentoService = Objects.requireNonNull(formaPagamentoService);
    }

    @Transactional
    public Pedido salvar(final Pedido pedido) {
        String novoCodigo = null;
        novoCodigo = UUID.randomUUID().toString().toLowerCase().replace("-", "");
        Usuario usuario = usuarioService.buscar(1L);
        pedido.setCodigo(novoCodigo);
        pedido.setCliente(usuario);
        this.validarPedido(pedido);
        this.validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    public Pedido buscar(final String codigo) {
        return this.buscarOuFalhar(codigo);
    }

    public Page<Pedido> pesquisar(PedidoFilter filtro, Pageable pageable) {
        return pedidoRepository.findAll(PedidoSpecification.usandoFiltro(filtro),pageable);
    }

    public void remover(final Long id) {
        try {
            if (this.pedidoRepository.existsById(id)) {
                this.pedidoRepository.deleteById(id);
            } else {
                throw new EntidadeNaoEncontradaException(
                        String.format(MSG_PEDIDO_NAO_ENCONTRADO, id));
            }
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PEDIDO_EM_USO, id));
        }
    }

    private void validarPedido(Pedido pedido) {
        Cidade cidade = cidadeService.buscar(pedido.getEnderecoEntrega().getCidade().getId());
        Usuario cliente = usuarioService.buscar(pedido.getCliente().getId());
        Restaurante restaurante = restauranteService.buscar(pedido.getRestaurante().getId());
        FormaPagamento formaPagamento = formaPagamentoService.buscar(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new NegocioException(String.format(
                    "Forma de pagamento '%s' não é aceita por esse restaurante.",
                    formaPagamento.getDescricao())
            );
        }
    }

    private void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
            Produto produto = produtoService.buscarOuFalhar(
                    pedido.getRestaurante().getId(), item.getProduto().getId());

            item.setPedido(pedido);
            item.setProduto(produto);
            item.setPrecoUnitario(produto.getPreco());
        });
    }

    private Pedido buscarOuFalhar(String codigo) {
        return pedidoRepository.findByCodigo(codigo).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_PEDIDO_NAO_ENCONTRADO, codigo))
        );
    }
}
