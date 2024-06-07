package br.com.mcm.apimcmfood.api.model.pedido;

import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteListResponse;
import br.com.mcm.apimcmfood.api.model.usuario.UsuarioResponse;
import com.fasterxml.jackson.annotation.JsonFilter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
@JsonFilter("pedidoFilter")
public class PedidoListResponse {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteListResponse restaurante;
    private UsuarioResponse cliente;

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
}
