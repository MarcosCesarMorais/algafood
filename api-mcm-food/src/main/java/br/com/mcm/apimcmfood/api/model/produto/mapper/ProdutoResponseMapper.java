package br.com.mcm.apimcmfood.api.model.produto.mapper;

import br.com.mcm.apimcmfood.api.model.produto.ProdutoListResponse;
import br.com.mcm.apimcmfood.api.model.produto.ProdutoResponse;
import br.com.mcm.apimcmfood.domain.entity.Produto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProdutoResponseMapper {

    private ModelMapper modelMapper;

    public ProdutoResponseMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public ProdutoResponse toResponse(final Produto produto) {
        return modelMapper.map(produto, ProdutoResponse.class);
    }

    private ProdutoListResponse toListResponse(final Produto produto) {
        return modelMapper.map(produto, ProdutoListResponse.class);
    }

    public List<ProdutoListResponse> toCollectionResponse(List<Produto> produtos) {
        return produtos.stream()
                .map(produto -> toListResponse(produto))
                .collect(Collectors.toList());
    }
}
