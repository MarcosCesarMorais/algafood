package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.api.model.cidade.CidadeListResponse;
import br.com.mcm.apimcmfood.api.model.cidade.CidadeRequest;
import br.com.mcm.apimcmfood.api.model.cidade.CidadeResponse;
import br.com.mcm.apimcmfood.api.model.cidade.mapper.CidadeRequestMapper;
import br.com.mcm.apimcmfood.api.model.cidade.mapper.CidadeResponseMapper;
import br.com.mcm.apimcmfood.domain.service.CidadeService;
import br.com.mcm.apimcmfood.domain.entity.Cidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    @Autowired
    private CidadeRequestMapper cidadeRequestMapper;

    @Autowired
    private CidadeResponseMapper cidadeResponseMapper;

    private CidadeService cidadeService;

    public CidadeController(final CidadeService cidadeService) {
        this.cidadeService = Objects.requireNonNull(cidadeService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeResponse adicionar(@Valid final @RequestBody CidadeRequest request) {
        return cidadeResponseMapper.toResponse(
                cidadeService.adicionar(cidadeRequestMapper.toDomain(request))
        );
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<CidadeResponse> buscar(@PathVariable("cidadeId") Long id) {
        return ResponseEntity.ok(
                cidadeResponseMapper.toResponse(cidadeService.buscar(id))
        );
    }

    @GetMapping
    public List<CidadeListResponse> listar() {
        return cidadeResponseMapper.toCollectionResponse(cidadeService.listar());
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<CidadeResponse> atualizar(
            final @PathVariable("cidadeId") Long id,
            final @Valid @RequestBody CidadeRequest request
    ) {
        Cidade cidadeAtual = cidadeService.buscar(id);
        cidadeRequestMapper.copyToDomainObject(request, cidadeAtual);

        return ResponseEntity.ok(cidadeResponseMapper.toResponse(cidadeService.atualizar(cidadeAtual)));
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<?> remover(final @PathVariable("cidadeId") Long id) {
        this.cidadeService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
