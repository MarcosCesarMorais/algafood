package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.api.model.grupo.GrupoListResponse;
import br.com.mcm.apimcmfood.api.model.grupo.GrupoRequest;
import br.com.mcm.apimcmfood.api.model.grupo.GrupoResponse;
import br.com.mcm.apimcmfood.api.model.grupo.mapper.GrupoRequestMapper;
import br.com.mcm.apimcmfood.api.model.grupo.mapper.GrupoResponseMapper;
import br.com.mcm.apimcmfood.domain.entity.Grupo;
import br.com.mcm.apimcmfood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/grupos")
public class GrupoController {

    @Autowired
    private GrupoRequestMapper grupoRequestMapper;

    @Autowired
    private GrupoResponseMapper grupoResponseMapper;

    private GrupoService grupoService;


    public GrupoController(GrupoService grupoService) {
        this.grupoService = Objects.requireNonNull(grupoService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoResponse adicionar(@Valid @RequestBody GrupoRequest request) {
        Grupo grupo = grupoRequestMapper.toDomain(request);
        return grupoResponseMapper.toResponse(grupoService.adicionar(grupo));
    }

    @GetMapping("/{grupoId}")
    public ResponseEntity<GrupoResponse> buscar(final @PathVariable("grupoId") Long id) {
        return ResponseEntity.ok(grupoResponseMapper.toResponse(grupoService.buscar(id)));
    }

    @GetMapping
    public List<GrupoListResponse> listar() {
        return grupoResponseMapper.toCollectionResponse(grupoService.listar());
    }

    @PutMapping("/{grupoId}")
    public ResponseEntity<GrupoResponse> atualizar(
            @PathVariable("grupoId") Long id,
            final @Valid @RequestBody GrupoRequest request
    ) {
        var grupo = grupoService.buscar(id);
        grupoRequestMapper.copyToDomainObject(request, grupo);
        return ResponseEntity.ok(grupoResponseMapper.toResponse(grupoService.atualizar(grupo)));
    }

    @DeleteMapping("/{grupoId}")
    public ResponseEntity<?> remover(final @PathVariable("grupoId") Long id) {
        grupoService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
