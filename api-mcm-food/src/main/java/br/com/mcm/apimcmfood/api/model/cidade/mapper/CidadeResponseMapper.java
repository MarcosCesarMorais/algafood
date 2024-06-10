package br.com.mcm.apimcmfood.api.model.cidade.mapper;

import br.com.mcm.apimcmfood.api.model.cidade.CidadeListResponse;
import br.com.mcm.apimcmfood.api.model.cidade.CidadeResponse;
import br.com.mcm.apimcmfood.domain.entity.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class CidadeResponseMapper {

    @Autowired
    private ModelMapper modelMapper;

    public CidadeResponse toResponse(Cidade cidade) {
        return modelMapper.map(cidade, CidadeResponse.class);
    }

    public CidadeListResponse toListResponse(Cidade cidade) {
        return modelMapper.map(cidade, CidadeListResponse.class);
    }

    public List<CidadeListResponse> toCollectionResponse(List<Cidade> cidades) {
        return cidades.stream()
                .map(cidade -> toListResponse(cidade))
                .collect(Collectors.toList());

    }
}
