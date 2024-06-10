package br.com.mcm.apimcmfood.domain.service.pedido;

import br.com.mcm.apimcmfood.domain.entity.Pedido;
import br.com.mcm.apimcmfood.domain.entity.StatusPedido;
import br.com.mcm.apimcmfood.domain.exception.NegocioException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
public class AlteracaoStatusPedidoService {

    private EmissaoPedidoService pedidoService;

    public AlteracaoStatusPedidoService(EmissaoPedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @Transactional
    public void confirmar(final String codigo) {
        Pedido pedido = pedidoService.buscar(codigo);
        pedido.confirmar();
    }

    @Transactional
    public void entregar(final String codigo) {
        Pedido pedido = pedidoService.buscar(codigo);
        pedido.entregar();
    }

    @Transactional
    public void cancelar(final String codigo) {
        Pedido pedido = pedidoService.buscar(codigo);
        pedido.cancelar();
    }
}
