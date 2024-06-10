package br.com.mcm.apimcmfood.api.model.pedido.mapper;

import br.com.mcm.apimcmfood.api.model.pedido.PedidoRequest;
import br.com.mcm.apimcmfood.domain.entity.Pedido;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PedidoRequestMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Pedido toDomain(PedidoRequest request) {
        return modelMapper.map(request, Pedido.class);
    }

    public void copyToDomainObject(PedidoRequest request, Pedido pedido) {
        modelMapper.map(request, pedido);
    }
}
