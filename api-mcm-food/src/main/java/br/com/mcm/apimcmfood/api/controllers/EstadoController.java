package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.api.model.estado.EstadoListResponse;
import br.com.mcm.apimcmfood.api.model.estado.EstadoRequest;
import br.com.mcm.apimcmfood.api.model.estado.EstadoResponse;
import br.com.mcm.apimcmfood.api.model.estado.mapper.EstadoRequestMapper;
import br.com.mcm.apimcmfood.api.model.estado.mapper.EstadoResponseMapper;
import br.com.mcm.apimcmfood.domain.service.EstadoService;
import br.com.mcm.apimcmfood.domain.entity.Estado;
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
@RequestMapping("/estados")
public class EstadoController {
    @Autowired
    private EstadoRequestMapper estadoRequestMapper;
    @Autowired
    private EstadoResponseMapper estadoResponseMapper;
    private EstadoService estadoService;

    public EstadoController(final EstadoService estadoService) {
        this.estadoService = Objects.requireNonNull(estadoService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoResponse adicionar(@Valid final @RequestBody EstadoRequest request) {
        return estadoResponseMapper.toResponse(
                estadoService.adicionar(estadoRequestMapper.toDomain(request))
        );
    }

    @GetMapping
    public List<EstadoListResponse> listar() {
        return estadoResponseMapper.toCollectionResponse(estadoService.listar());
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<EstadoResponse> buscar(final @PathVariable("estadoId") Long id) {
        return ResponseEntity.ok(
                estadoResponseMapper.toResponse(estadoService.buscar(id))
        );
    }

    @PutMapping("/{estadoId}")
    public ResponseEntity<EstadoResponse> atualizar(
            final @PathVariable("estadoId") Long id,
            final @Valid @RequestBody EstadoRequest request
    ) {
        Estado estadoAtual = estadoService.buscar(id);
        estadoRequestMapper.copyToDomainObject(request,estadoAtual);
        return ResponseEntity.ok(estadoResponseMapper.toResponse(estadoService.atualizar(id, estadoAtual)));
    }

    @DeleteMapping("/{estadoId}")
    public ResponseEntity<?> remover(final @PathVariable("estadoId") Long id) {
        this.estadoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
