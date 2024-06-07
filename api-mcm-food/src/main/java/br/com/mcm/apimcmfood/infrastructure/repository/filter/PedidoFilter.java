package br.com.mcm.apimcmfood.infrastructure.repository.filter;

import java.time.OffsetDateTime;

public class PedidoFilter {

    private Long clienteId;
    private Long restauratneId;
    private OffsetDateTime dataCriacaoInicio;
    private OffsetDateTime dataCriacaoFim;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getRestauratneId() {
        return restauratneId;
    }

    public void setRestauratneId(Long restauratneId) {
        this.restauratneId = restauratneId;
    }

    public OffsetDateTime getDataCriacaoInicio() {
        return dataCriacaoInicio;
    }

    public void setDataCriacaoInicio(OffsetDateTime dataCriacaoInicio) {
        this.dataCriacaoInicio = dataCriacaoInicio;
    }

    public OffsetDateTime getDataCriacaoFim() {
        return dataCriacaoFim;
    }

    public void setDataCriacaoFim(OffsetDateTime dataCriacaoFim) {
        this.dataCriacaoFim = dataCriacaoFim;
    }
}
