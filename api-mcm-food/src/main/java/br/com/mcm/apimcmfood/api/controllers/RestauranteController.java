package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.api.model.restaurante.mapper.RestauranteRequestMapper;
import br.com.mcm.apimcmfood.api.model.restaurante.mapper.RestauranteResponseMapper;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteListResponse;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteRequest;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteResponse;
import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.exception.NegocioException;
import br.com.mcm.apimcmfood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRequestMapper restauranteRequestMapper;
    @Autowired
    private RestauranteResponseMapper restauranteResponseMapper;
    private RestauranteService restauranteService;

    public RestauranteController(
            final RestauranteService restauranteService
    ) {
        this.restauranteService = Objects.requireNonNull(restauranteService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteResponse adicionar(@Valid @RequestBody RestauranteRequest request) {
        Restaurante restaurante = restauranteRequestMapper.toDomain(request);
        return restauranteResponseMapper.toResponse(restauranteService.adicionar(restaurante));
    }

    @GetMapping
    public List<RestauranteListResponse> listar() {
        return restauranteResponseMapper.toCollectionResponse(restauranteService.listar());
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<RestauranteResponse> buscar(@PathVariable("restauranteId") Long id) {
        return ResponseEntity.ok(restauranteResponseMapper.toResponse(restauranteService.buscar(id)));
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<RestauranteResponse> atualizar(
            final @PathVariable("restauranteId") Long id,
            final @Valid @RequestBody RestauranteRequest request
    ) {
        Restaurante restauranteAtual = restauranteService.buscar(id);
        restauranteRequestMapper.copyToDomainObject(request, restauranteAtual);
        return ResponseEntity.ok(restauranteResponseMapper.toResponse(restauranteService.atualizar(restauranteAtual)));
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<?> remover(@PathVariable("restauranteId") Long id) {
        this.restauranteService.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(final @PathVariable("restauranteId") Long id) {
        restauranteService.ativar(id);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(final @PathVariable("restauranteId") Long id) {
        restauranteService.inativar(id);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(final @RequestBody List<Long> restauranteIds) {
        restauranteService.ativar(restauranteIds);
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(final @RequestBody List<Long> restauranteIds) {
        try {
            restauranteService.inativar(restauranteIds);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrirRestaurante(final @PathVariable("restauranteId") Long id) {
        try {
            restauranteService.abrirRestaurante(id);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void fecharRestaurante(final @PathVariable("restauranteId") Long id) {
        restauranteService.fecharRestaurante(id);
    }
}
