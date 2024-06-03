package br.com.mcm.apimcmfood.infrastructure.config;

import br.com.mcm.apimcmfood.api.model.endereco.EnderecoListResponse;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteListResponse;
import br.com.mcm.apimcmfood.domain.entity.Endereco;
import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();
        //modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);
        var enderecoToEnderecoListResponse = modelMapper.createTypeMap(
                Endereco.class, EnderecoListResponse.class);

        enderecoToEnderecoListResponse.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getEstado().getNome(),
                (enderecolistDest, value) -> enderecolistDest.getEstado());

        enderecoToEnderecoListResponse.<String>addMapping(
                enderecoSrc -> enderecoSrc.getCidade().getNome(),
                (enderecolistDest, value) -> enderecolistDest.getCidade());

        var restauranteToRestauranteListResponse = modelMapper.createTypeMap(
                Restaurante.class, RestauranteListResponse.class);

        restauranteToRestauranteListResponse.<String>addMapping(
                restauranteSrc -> restauranteSrc.getCozinha().getNome(),
                (restaurantelistDest, value) -> restaurantelistDest.getCozinha());

        return modelMapper;
    }
}
