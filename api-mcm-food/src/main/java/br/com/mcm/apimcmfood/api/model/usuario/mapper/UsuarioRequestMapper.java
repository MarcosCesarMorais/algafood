package br.com.mcm.apimcmfood.api.model.usuario.mapper;

import br.com.mcm.apimcmfood.api.model.usuario.UsuarioRequest;
import br.com.mcm.apimcmfood.domain.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioRequestMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomain(UsuarioRequest response) {
        return modelMapper.map(response, Usuario.class);
    }

    public void copyToDomainObject(UsuarioRequest request, Usuario usuario) {
        modelMapper.map(request, usuario);
    }
}
