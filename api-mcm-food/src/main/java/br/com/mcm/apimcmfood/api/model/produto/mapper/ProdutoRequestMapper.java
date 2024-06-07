package br.com.mcm.apimcmfood.api.model.produto.mapper;

import br.com.mcm.apimcmfood.api.model.pedido.PedidoRequest;
import br.com.mcm.apimcmfood.api.model.produto.ProdutoRequest;
import br.com.mcm.apimcmfood.domain.entity.Pedido;
import br.com.mcm.apimcmfood.domain.entity.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProdutoRequestMapper {

    private ModelMapper modelMapper;

    public ProdutoRequestMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Produto toDomain(final ProdutoRequest produtoRequest) {
        return modelMapper.map(produtoRequest, Produto.class);
    }

    public void copyToDomain(final ProdutoRequest produtoRequest, final Produto produto){
        modelMapper.map(produtoRequest, produto);
    }
}
