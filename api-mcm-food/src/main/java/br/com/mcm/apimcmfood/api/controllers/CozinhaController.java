package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.domain.entity.Cozinha;
import br.com.mcm.apimcmfood.application.service.CozinhaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {
    private CozinhaService cozinhaService;

    public CozinhaController(
            final CozinhaService cozinhaService
    ) {
        this.cozinhaService = Objects.requireNonNull(cozinhaService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@Valid @RequestBody Cozinha cozinha) {
        return cozinhaService.adicionar(cozinha);
    }

    @GetMapping
    public Page<Cozinha> listar(Pageable pageable) {
        return cozinhaService.listar(pageable);
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {
        return ResponseEntity.ok(cozinhaService.buscar(id));
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(
            @Valid
            final @PathVariable("cozinhaId") Long id,
            final @RequestBody Cozinha cozinha
    ) {
        return ResponseEntity.ok(cozinhaService.atualizar(id, cozinha));
    }
    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> remover(@PathVariable("cozinhaId") Long id) {
        this.cozinhaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
