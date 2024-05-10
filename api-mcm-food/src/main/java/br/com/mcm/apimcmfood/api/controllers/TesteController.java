package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import br.com.mcm.apimcmfood.infrastructure.repository.RestauranteRepository;
import br.com.mcm.apimcmfood.infrastructure.repository.spec.RestauranteSpecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static br.com.mcm.apimcmfood.infrastructure.repository.spec.RestauranteSpecs.comFreteGratis;
import static br.com.mcm.apimcmfood.infrastructure.repository.spec.RestauranteSpecs.comNomeSemelhante;

@RestController
@RequestMapping(value="teste")
public class TesteController {

    @Autowired
    private RestauranteRepository restauranteRepository;
    @GetMapping("/restaurante/com-fretes-gratis")
    public List<Restaurante> RestauranteComFreteGratis(String nome){
        return restauranteRepository.findComFreteGratis(nome);
    }

    @GetMapping("/restaurante/com-nome-semelhante")
    public List<Restaurante> RestauranteComNomeSemelhante(String nome){
        return restauranteRepository.findAll(comFreteGratis().and(comNomeSemelhante(nome)));
    }

    @GetMapping("/restaurante/primeira")
    public Optional<Restaurante> restaurantePrimeiro(){
        return restauranteRepository.buscarPrimeiro();
    }


}
