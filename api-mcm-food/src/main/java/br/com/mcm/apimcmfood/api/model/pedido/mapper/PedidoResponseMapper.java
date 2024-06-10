package br.com.mcm.apimcmfood.api.model.pedido.mapper;

import br.com.mcm.apimcmfood.api.model.pedido.PedidoResponse;
import br.com.mcm.apimcmfood.domain.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoResponseMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoResponse toResponse(Pedido pedido) {
        return modelMapper.map(pedido, PedidoResponse.class);
    }

    public List<PedidoResponse> toCollectionModel(List<Pedido> pedidos) {
        return pedidos.stream()
                .map(pedido -> toResponse(pedido))
                .collect(Collectors.toList());
    }
}
