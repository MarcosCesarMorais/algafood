package br.com.mcm.apimcmfood.api.model.pedido;

import br.com.mcm.apimcmfood.api.model.endereco.EnderecoRequest;
import br.com.mcm.apimcmfood.api.model.endereco.EnderecoResponse;
import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoIdRequest;
import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoResponse;
import br.com.mcm.apimcmfood.api.model.itemPedido.ItemPedidoResponse;
import br.com.mcm.apimcmfood.api.model.itemPedido.ItemPedidoResquest;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteIdRequest;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteResumidoResponse;
import br.com.mcm.apimcmfood.api.model.usuario.UsuarioResponse;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PedidoRequest {

    @Valid
    @NotNull
    private RestauranteIdRequest restaurante;

    @Valid
    @NotNull
    private EnderecoRequest enderecoEntrega;

    @Valid
    @NotNull
    private FormaPagamentoIdRequest formaPagamento;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoResquest> itens;
}
