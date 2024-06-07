package br.com.mcm.apimcmfood.api.model.pedido.mapper;

import br.com.mcm.apimcmfood.api.model.pedido.PedidoListResponse;
import br.com.mcm.apimcmfood.domain.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoListResponseMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PedidoListResponse toListResponse (Pedido pedido){
        return modelMapper.map(pedido, PedidoListResponse.class);
    }

    public List<PedidoListResponse> toCollectionResponse(List<Pedido> pedidos){
        return pedidos.stream()
                .map(pedido -> toListResponse(pedido))
                .collect(Collectors.toList());
    }
}
