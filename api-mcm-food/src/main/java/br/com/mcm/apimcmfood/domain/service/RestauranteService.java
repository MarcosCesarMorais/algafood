package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.model.Restaurante;
import br.com.mcm.apimcmfood.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;
@Service
public class RestauranteService {

    private RestauranteRepository restauranteRepository;

    public RestauranteService(
            final RestauranteRepository restauranteRepository
    ) {
        this.restauranteRepository = Objects.requireNonNull(restauranteRepository);
    }

    public Restaurante adicionar(final Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }

    public Restaurante buscar(final Long id) {
        return restauranteRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Não foi possível encontrar um restaurante com o código %d na base de dados.", id)));
    }

    public Page<Restaurante> listar(final Pageable pageable) {
        return restauranteRepository.findAll(pageable);
    }

    public Restaurante atualizar(Long id, Restaurante restaurante) {
        var restauranteAtual = restauranteRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Não foi possível encontrar um restaurante com o código %d na base de dados.", id)));
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
        return this.restauranteRepository.save(restauranteAtual);
    }

    public void remover(final Long id) {
        if (this.restauranteRepository.existsById(id)) {
            this.restauranteRepository.deleteById(id);
        } else {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não foi possível encontrar um restaurante com o código %d na base de dados.", id));
        }
    }
}
