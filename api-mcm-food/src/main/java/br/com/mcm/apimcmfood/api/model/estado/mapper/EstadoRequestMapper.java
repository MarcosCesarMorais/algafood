package br.com.mcm.apimcmfood.api.model.estado.mapper;

import br.com.mcm.apimcmfood.api.model.estado.EstadoRequest;
import br.com.mcm.apimcmfood.domain.entity.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoRequestMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Estado toDomain(EstadoRequest request){
        return modelMapper.map(request, Estado.class);
    }

    public void copyToDomainObject(EstadoRequest request, Estado estado){

        modelMapper.map(request, estado);
    }
}
