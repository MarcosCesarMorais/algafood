package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.application.service.EstadoService;
import br.com.mcm.apimcmfood.domain.entity.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    private EstadoService estadoService;

    public EstadoController(final EstadoService estadoService) {
        this.estadoService = Objects.requireNonNull(estadoService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado adicionar(final @RequestBody Estado estado) {
        return estadoService.adicionar(estado);
    }

    @GetMapping
    public Page<Estado> listar(final Pageable pageable) {
        return estadoService.listar(pageable);
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<Estado> buscar(final @PathVariable("estadoId") Long id) {
        return ResponseEntity.ok(estadoService.buscar(id));
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<Estado> atualizar(
            final @PathVariable("estadoId") Long id,
            final @RequestBody Estado estado
    ) {
        return ResponseEntity.ok(estadoService.atualizar(id, estado));
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> remover (final @PathVariable("estadoId")Long id){
        this.estadoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
