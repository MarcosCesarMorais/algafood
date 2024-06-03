package br.com.mcm.apimcmfood.api.model.usuario.mapper;

import br.com.mcm.apimcmfood.api.model.usuario.UsuarioListResponse;
import br.com.mcm.apimcmfood.api.model.usuario.UsuarioResponse;
import br.com.mcm.apimcmfood.domain.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UsuarioResponseMapper {

    @Autowired
    private ModelMapper modelMapper;

    public UsuarioResponse toResponse(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioResponse.class);
    }

    public UsuarioListResponse toListResponse(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioListResponse.class);
    }

    public List<UsuarioListResponse> toCollectionModel(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(usuario -> toListResponse(usuario))
                .collect(Collectors.toList());
    }
}
