package br.com.mcm.apimcmfood.api.model.permissao.mapper;

import br.com.mcm.apimcmfood.api.model.permissao.PermissaoListResponse;
import br.com.mcm.apimcmfood.api.model.permissao.PermissaoResponse;
import br.com.mcm.apimcmfood.domain.entity.Permissao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PermissaoResponseMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PermissaoResponse toResponse(final Permissao permissao) {
        return modelMapper.map(permissao, PermissaoResponse.class);
    }

    public PermissaoListResponse toListResponse(final Permissao permissao) {
        return modelMapper.map(permissao, PermissaoListResponse.class);
    }

    public List<PermissaoListResponse> toCollectionResponse(Collection<Permissao> permissoes) {
        return permissoes
                .stream()
                .map((permissao) -> toListResponse(permissao))
                .collect(Collectors.toList());
    }
}
