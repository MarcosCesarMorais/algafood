package br.com.mcm.apimcmfood.application.service;

import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import br.com.mcm.apimcmfood.domain.exception.NegocioException;
import br.com.mcm.apimcmfood.infrastructure.repository.RestauranteRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Objects;
@Service
public class RestauranteService {

    private RestauranteRepository restauranteRepository;
    private CozinhaService cozinhaService;

    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO
            = "Não existe um cadastro de restaurante com código %d";

    private static final String MSG_RESTAURANTE_COZINHA_INVALIDA
            = "Cozinha com código %d não existe, favor informar uma cozinha válida";

    public RestauranteService(
            final RestauranteRepository restauranteRepository,
            final CozinhaService cozinhaService
    ) {
        this.restauranteRepository = Objects.requireNonNull(restauranteRepository);
        this.cozinhaService = Objects.requireNonNull(cozinhaService);
    }

    public Restaurante adicionar(final Restaurante restaurante) {
        var cozinhaId = restaurante.getCozinha().getId();
        try{
            var cozinhaAtual = cozinhaService.buscar(cozinhaId);
            restaurante.setCozinha(cozinhaAtual);
            return this.restauranteRepository.save(restaurante);
        }catch(EntidadeNaoEncontradaException e){
            throw new NegocioException(
                    String.format(MSG_RESTAURANTE_COZINHA_INVALIDA, cozinhaId));
        }
    }

    public Restaurante buscar(final Long id) {
        return restauranteRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id)));
    }

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Restaurante atualizar(Long id, Restaurante restaurante) {
        var restauranteAtual = restauranteRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id)));
        BeanUtils.copyProperties(restaurante, restauranteAtual, "id");
        return this.restauranteRepository.save(restauranteAtual);
    }

    public Restaurante atualizarParcial(Long id, Map<String, Object> campos){
        var restauranteAtual = restauranteRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id)));
        merge(campos, restauranteAtual);
        return this.restauranteRepository.save(restauranteAtual);
    }

    public void remover(final Long id) {
        if (this.restauranteRepository.existsById(id)) {
            this.restauranteRepository.deleteById(id);
        } else {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id));
        }
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino){
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
        dadosOrigem.forEach((nomePropriedade, valorPropriedade)->{
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);
            Object valorAtualizado = ReflectionUtils.getField(field, restauranteOrigem);
            ReflectionUtils.setField(field, restauranteDestino, valorAtualizado);
        });
    }
}
