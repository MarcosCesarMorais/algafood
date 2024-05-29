package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.api.model.restaurante.mapper.RestauranteRequestMapper;
import br.com.mcm.apimcmfood.domain.entity.Cozinha;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import br.com.mcm.apimcmfood.domain.exception.NegocioException;
import br.com.mcm.apimcmfood.infrastructure.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRequestMapper restauranteRequestMapper;
    private RestauranteRepository restauranteRepository;
    private CozinhaService cozinhaService;

    private static final String MSG_RESTAURANTE_NAO_ENCONTRADO
            = "Não existe um cadastro de restaurante com código %d";

    private static final String MSG_RESTAURANTE_COZINHA_INVALIDA
            = "Cozinha com código %d não existe, favor informar uma cozinha válida";

    private static final String MSG_COZINHA_EM_USO =
            "Não é possível remover o restaurante com o código %d, pois está associada a uma ou mais cozinhas.";

    public RestauranteService(final RestauranteRepository restauranteRepository, final CozinhaService cozinhaService) {
        this.restauranteRepository = Objects.requireNonNull(restauranteRepository);
        this.cozinhaService = Objects.requireNonNull(cozinhaService);
    }
    @Transactional
    public Restaurante adicionar(final Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();

        try {
            Cozinha cozinhaAtual = cozinhaService.buscar(cozinhaId);
            restaurante.setCozinha(cozinhaAtual);
            return salvarRestaurante(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(
                    String.format(MSG_RESTAURANTE_COZINHA_INVALIDA, cozinhaId));
        }
    }

    public Restaurante buscar(final Long id) {
        return buscarOuFalhar(id);
    }

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public Restaurante atualizar( final Restaurante restaurante) {
        try {
            return salvarRestaurante(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    public void remover(final Long id) {
        try {
            restauranteRepository.deleteById(id);
            restauranteRepository.flush();
        } catch (EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(
                    toString().formatted(MSG_RESTAURANTE_NAO_ENCONTRADO, id)
            );
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, id)
            );
        }
    }

    private Restaurante buscarOuFalhar(final Long id) {
        return restauranteRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_RESTAURANTE_NAO_ENCONTRADO, id)));
    }
    @Transactional
    private Restaurante salvarRestaurante(final Restaurante restaurante) {
        return restauranteRepository.save(restaurante);
    }
}
