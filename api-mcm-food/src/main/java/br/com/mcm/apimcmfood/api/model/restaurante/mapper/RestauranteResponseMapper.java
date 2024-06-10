package br.com.mcm.apimcmfood.api.model.restaurante.mapper;

import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteListResponse;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteResponse;
import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteResponseMapper {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteResponse toResponse(Restaurante restaurante){
        return modelMapper.map(restaurante, RestauranteResponse.class);
    }

    public RestauranteListResponse toListResponse(Restaurante restaurante){
        return modelMapper.map(restaurante, RestauranteListResponse.class);
    }

    public List<RestauranteListResponse> toCollectionResponse(List<Restaurante> restaurantes){
        return restaurantes.stream()
                .map(restaurante -> toListResponse(restaurante))
                .collect(Collectors.toList());
    }
}
