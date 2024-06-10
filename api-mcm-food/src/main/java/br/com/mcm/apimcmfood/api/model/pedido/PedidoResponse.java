package br.com.mcm.apimcmfood.api.model.pedido;

import br.com.mcm.apimcmfood.api.model.endereco.EnderecoResponse;
import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoResponse;
import br.com.mcm.apimcmfood.api.model.itemPedido.ItemPedidoResponse;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteListResponse;
import br.com.mcm.apimcmfood.api.model.usuario.UsuarioResponse;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

public class PedidoResponse {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private RestauranteListResponse restaurante;
    private UsuarioResponse cliente;
    private FormaPagamentoResponse formaPagamento;
    private EnderecoResponse enderecoEntrega;
    private List<ItemPedidoResponse> itens;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getTaxaFrete() {
        return taxaFrete;
    }

    public void setTaxaFrete(BigDecimal taxaFrete) {
        this.taxaFrete = taxaFrete;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public OffsetDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(OffsetDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public OffsetDateTime getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(OffsetDateTime dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public OffsetDateTime getDataEntrega() {
        return dataEntrega;
    }

    public void setDataEntrega(OffsetDateTime dataEntrega) {
        this.dataEntrega = dataEntrega;
    }

    public OffsetDateTime getDataCancelamento() {
        return dataCancelamento;
    }

    public void setDataCancelamento(OffsetDateTime dataCancelamento) {
        this.dataCancelamento = dataCancelamento;
    }

    public RestauranteListResponse getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(RestauranteListResponse restaurante) {
        this.restaurante = restaurante;
    }

    public UsuarioResponse getCliente() {
        return cliente;
    }

    public void setCliente(UsuarioResponse cliente) {
        this.cliente = cliente;
    }

    public FormaPagamentoResponse getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(FormaPagamentoResponse formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public EnderecoResponse getEnderecoEntrega() {
        return enderecoEntrega;
    }

    public void setEnderecoEntrega(EnderecoResponse enderecoEntrega) {
        this.enderecoEntrega = enderecoEntrega;
    }

    public List<ItemPedidoResponse> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedidoResponse> itens) {
        this.itens = itens;
    }
}
