package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.api.model.cozinha.CozinhaListResponse;
import br.com.mcm.apimcmfood.api.model.cozinha.CozinhaRequest;
import br.com.mcm.apimcmfood.api.model.cozinha.CozinhaResponse;
import br.com.mcm.apimcmfood.api.model.cozinha.mapper.CozinhaRequestMapper;
import br.com.mcm.apimcmfood.api.model.cozinha.mapper.CozinhaResponseMapper;
import br.com.mcm.apimcmfood.domain.entity.Cozinha;
import br.com.mcm.apimcmfood.domain.service.CozinhaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRequestMapper cozinhaRequestMapper;
    @Autowired
    private CozinhaResponseMapper cozinhaResponseMapper;
    private CozinhaService cozinhaService;

    public CozinhaController(
            final CozinhaService cozinhaService
    ) {
        this.cozinhaService = Objects.requireNonNull(cozinhaService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CozinhaResponse adicionar(
            final @Valid @RequestBody CozinhaRequest request
    ) {
        return cozinhaResponseMapper.toResponse(
                cozinhaService.adicionar(cozinhaRequestMapper.toDomain(request))
        );
    }

    @GetMapping
    public Page<CozinhaListResponse> listar(Pageable pageable) {
        return cozinhaResponseMapper.toPageableToResponse(cozinhaService.listar(pageable));
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaResponse> buscar(final @PathVariable("cozinhaId") Long id) {
        return ResponseEntity.ok(
                cozinhaResponseMapper.toResponse(cozinhaService.buscar(id))
        );
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<CozinhaResponse> atualizar(
            final @PathVariable("cozinhaId") Long id,
            @Valid final @RequestBody Cozinha cozinha
    ) {
        return ResponseEntity.ok(
                cozinhaResponseMapper.toResponse(cozinhaService.atualizar(id, cozinha))
        );
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<?> remover(@PathVariable("cozinhaId") Long id) {
        this.cozinhaService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
