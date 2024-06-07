package br.com.mcm.apimcmfood.api.model.pedido;

import br.com.mcm.apimcmfood.api.model.endereco.EnderecoRequest;
import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoIdRequest;
import br.com.mcm.apimcmfood.api.model.itemPedido.ItemPedidoResquest;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteIdRequest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    public RestauranteIdRequest getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(RestauranteIdRequest restaurante) {
        this.restaurante = restaurante;
    }

    public EnderecoRequest getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(EnderecoRequest enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public FormaPagamentoIdRequest getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamentoIdRequest formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public List<ItemPedidoResquest> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoResquest> itens) {
        this.itens = itens;
    }
}
