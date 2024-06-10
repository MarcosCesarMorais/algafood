package br.com.mcm.apimcmfood.api.model.grupo.mapper;

import br.com.mcm.apimcmfood.api.model.grupo.GrupoRequest;
import br.com.mcm.apimcmfood.domain.entity.Grupo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GrupoRequestMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Grupo toDomain(GrupoRequest request) {
        return modelMapper.map(request, Grupo.class);
    }

    public void copyToDomainObject(GrupoRequest resquest, Grupo grupo) {
        modelMapper.map(resquest, grupo);
    }
}
