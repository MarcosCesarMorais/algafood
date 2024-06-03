package br.com.mcm.apimcmfood.api.model.permissao.mapper;

import br.com.mcm.apimcmfood.api.model.permissao.PermissaoRequest;
import br.com.mcm.apimcmfood.domain.entity.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissaoRequestMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Permissao toDomain(final PermissaoRequest request) {
        return modelMapper.map(request, Permissao.class);
    }

    public void copyToDomainObject(final PermissaoRequest request, final Permissao permissao) {
        modelMapper.map(request, permissao);
    }
}
