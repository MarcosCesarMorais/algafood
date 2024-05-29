package br.com.mcm.apimcmfood.api.model.restaurante.mapper;

import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteRequest;
import br.com.mcm.apimcmfood.domain.entity.Cozinha;
import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteRequestMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomain(RestauranteRequest request){
        return modelMapper.map(request, Restaurante.class);
    }

    public void copyToDomainObject(RestauranteRequest request, Restaurante restaurante){
        // Para evitar org.hibernate.HibernateException: identifier of an instance of
        // com.algaworks.algafood.domain.model.Cozinha was altered from 1 to 2
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(request, restaurante);
    }
}
