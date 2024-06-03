package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoListResponse;
import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoRequest;
import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoResponse;
import br.com.mcm.apimcmfood.api.model.formaPagamento.mapper.FormaPagamentoRequestMapper;
import br.com.mcm.apimcmfood.api.model.formaPagamento.mapper.FormaPagamentoResponseMapper;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteListResponse;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteRequest;
import br.com.mcm.apimcmfood.api.model.restaurante.RestauranteResponse;
import br.com.mcm.apimcmfood.api.model.restaurante.mapper.RestauranteRequestMapper;
import br.com.mcm.apimcmfood.api.model.restaurante.mapper.RestauranteResponseMapper;
import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import br.com.mcm.apimcmfood.domain.service.RestauranteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    @Autowired
    private RestauranteRequestMapper restauranteRequestMapper;
    @Autowired
    private RestauranteResponseMapper restauranteResponseMapper;
    @Autowired
    private FormaPagamentoResponseMapper formaPagamentoResponseMapper;
    @Autowired
    private FormaPagamentoRequestMapper formaPagamentoRequestMapper;
    private RestauranteService restauranteService;

    public RestauranteFormaPagamentoController(
            final RestauranteService restauranteService
    ) {
        this.restauranteService = Objects.requireNonNull(restauranteService);
    }

    @GetMapping
    public List<FormaPagamentoListResponse> listar(@PathVariable Long restauranteId) {
        Restaurante restaurante = restauranteService.buscar(restauranteId);
        return formaPagamentoResponseMapper.toCollectionResponse(restaurante.getFormasPagamento());
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.desassociarFormaPagamento(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId) {
        restauranteService.associarFormaPagamento(restauranteId, formaPagamentoId);
    }


}
