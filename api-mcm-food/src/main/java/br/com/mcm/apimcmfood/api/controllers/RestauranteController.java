package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import br.com.mcm.apimcmfood.application.service.RestauranteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

    private RestauranteService restauranteService;

    public RestauranteController(
            final RestauranteService restauranteService
    ) {
        this.restauranteService = Objects.requireNonNull(restauranteService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante adicionar(@Valid @RequestBody Restaurante restaurante) {
        return restauranteService.adicionar(restaurante);
    }

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteService.listar();
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id) {
        return ResponseEntity.ok(restauranteService.buscar(id));
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> atualizar(
            final @PathVariable("restauranteId") Long id,
            final @RequestBody Restaurante restaurante
    ) {
        return ResponseEntity.ok(restauranteService.atualizar(id, restaurante));
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> atualizarParcial(
            final @PathVariable("restauranteId") Long id,
            final @RequestBody Map<String, Object> campos,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(restauranteService.atualizarParcial(id, campos, request));
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> remover(@PathVariable("restauranteId") Long id) {
        this.restauranteService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
