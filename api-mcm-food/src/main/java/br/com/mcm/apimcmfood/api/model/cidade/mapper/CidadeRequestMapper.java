package br.com.mcm.apimcmfood.api.model.cidade.mapper;

import br.com.mcm.apimcmfood.api.model.cidade.CidadeRequest;
import br.com.mcm.apimcmfood.domain.entity.Cidade;
import br.com.mcm.apimcmfood.domain.entity.Estado;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CidadeRequestMapper {

    @Autowired
    private ModelMapper modelMapper;

    public Cidade toDomain(CidadeRequest request) {
        return modelMapper.map(request, Cidade.class);
    }

    public void copyToDomainObject(CidadeRequest request, Cidade cidade) {
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Cidade was altered from 1 to 2
        cidade.setEstado(new Estado());
        modelMapper.map(request, cidade);
    }

}
