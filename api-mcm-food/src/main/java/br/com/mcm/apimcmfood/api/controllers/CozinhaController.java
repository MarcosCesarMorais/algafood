package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.domain.model.Cozinha;
import br.com.mcm.apimcmfood.domain.repository.CozinhaRepository;
import br.com.mcm.apimcmfood.domain.service.CozinhaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/cozinhas")
public class CozinhaController {
    private CozinhaRepository repository;
    private CozinhaService cozinhaService;

    public CozinhaController(
            final CozinhaRepository repository,
            final CozinhaService cozinhaService
    ) {
        this.repository = Objects.requireNonNull(repository);
        this.cozinhaService = Objects.requireNonNull(cozinhaService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        cozinhaService.adicionar(cozinha);
        return cozinha;
    }

    @GetMapping
    public List<Cozinha> listar() {
        return repository.findAll();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {
        return ResponseEntity.ok(cozinhaService.buscar(id));
    }

    @PutMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(
            final @PathVariable("cozinhaId") Long id,
            final @RequestBody Cozinha cozinha
    ) {
        var cozinhaAtual = repository.findById(id);
        if (cozinhaAtual.isPresent()) {
            BeanUtils.copyProperties(cozinha, cozinhaAtual.get(), "id");
            repository.save(cozinhaAtual.get());
            return ResponseEntity.ok(cozinhaAtual.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> remover(@PathVariable("cozinhaId") Long id) {
        try {
            var cozinha = repository.findById(id);
            if (cozinha.isPresent()) {
                repository.deleteById(id);
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.notFound().build();
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}
