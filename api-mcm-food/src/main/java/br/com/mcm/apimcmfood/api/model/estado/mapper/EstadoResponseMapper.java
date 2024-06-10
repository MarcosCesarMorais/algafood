package br.com.mcm.apimcmfood.api.model.estado.mapper;

import br.com.mcm.apimcmfood.api.model.estado.EstadoListResponse;
import br.com.mcm.apimcmfood.api.model.estado.EstadoResponse;
import br.com.mcm.apimcmfood.domain.entity.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EstadoResponseMapper {

    @Autowired
    private ModelMapper modelMapper;

    public EstadoResponse toResponse(Estado estado){
        return modelMapper.map(estado, EstadoResponse.class);
    }

    public EstadoListResponse toListResponse(Estado estado){
        return modelMapper.map(estado, EstadoListResponse.class);
    }

    public List<EstadoListResponse> toCollectionResponse(List<Estado> estados){
        return estados.stream()
                .map(estado -> toListResponse(estado))
                .collect(Collectors.toList());
    }
}
