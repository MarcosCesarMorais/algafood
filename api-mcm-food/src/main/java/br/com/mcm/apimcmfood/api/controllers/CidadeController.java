package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.domain.service.CidadeService;
import br.com.mcm.apimcmfood.domain.entity.Cidade;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/cidades")
public class CidadeController {

    private CidadeService cidadeService;

    public CidadeController(final CidadeService cidadeService) {
        this.cidadeService = Objects.requireNonNull(cidadeService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade adicionar(@Valid final @RequestBody Cidade cidade) {
        return cidadeService.adicionar(cidade);
    }

    @GetMapping("/{cidadeId}")
    public ResponseEntity<Cidade> buscar(@PathVariable("cidadeId") Long id) {
        return ResponseEntity.ok(cidadeService.buscar(id));
    }

    @GetMapping
    public List<Cidade> listar() {
        return cidadeService.listar();
    }

    @PutMapping("/{cidadeId}")
    public ResponseEntity<Cidade> atualizar(
            @Valid
            final @PathVariable("cidadeId") Long id,
            final @RequestBody Cidade cidade
    ) {
        return ResponseEntity.ok(cidadeService.atualizar(id, cidade));
    }

    @DeleteMapping("/{cidadeId}")
    public ResponseEntity<Cidade> remover(final @PathVariable("cidadeId") Long id) {
        this.cidadeService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
