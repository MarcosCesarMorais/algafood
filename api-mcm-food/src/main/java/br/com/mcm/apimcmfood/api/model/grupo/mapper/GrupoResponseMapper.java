package br.com.mcm.apimcmfood.api.model.grupo.mapper;

import br.com.mcm.apimcmfood.api.model.grupo.GrupoListResponse;
import br.com.mcm.apimcmfood.api.model.grupo.GrupoResponse;
import br.com.mcm.apimcmfood.domain.entity.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class GrupoResponseMapper {

    @Autowired
    private ModelMapper modelMapper;

    public GrupoResponse toResponse(Grupo grupo) {
        return modelMapper.map(grupo, GrupoResponse.class);
    }

    public GrupoListResponse toListResponse(Grupo grupo) {
        return modelMapper.map(grupo, GrupoListResponse.class);
    }

    public List<GrupoListResponse> toCollectionResponse(Collection<Grupo> grupos) {
        return grupos
                .stream()
                .map(grupo -> toListResponse(grupo))
                .collect(Collectors.toList());
    }
}
