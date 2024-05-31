package br.com.mcm.apimcmfood.api.model.cozinha.mapper;

import br.com.mcm.apimcmfood.api.model.cozinha.CozinhaRequest;
import br.com.mcm.apimcmfood.domain.entity.Cozinha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaRequestMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Cozinha toDomain(CozinhaRequest request) {
        return modelMapper.map(request, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaRequest request, Cozinha cozinha) {
        modelMapper.map(request, cozinha);
    }
}
