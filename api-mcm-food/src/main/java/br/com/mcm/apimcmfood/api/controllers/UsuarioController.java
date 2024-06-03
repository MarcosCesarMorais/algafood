package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.api.model.usuario.*;
import br.com.mcm.apimcmfood.api.model.usuario.mapper.UsuarioRequestMapper;
import br.com.mcm.apimcmfood.api.model.usuario.mapper.UsuarioResponseMapper;
import br.com.mcm.apimcmfood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioResponseMapper usuarioResponseMapper;

    @Autowired
    private UsuarioRequestMapper usuarioRequestMapper;

    private UsuarioService usuarioService;


    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = Objects.requireNonNull(usuarioService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioResponse adicionar(final @RequestBody @Valid UsuarioComSenhaRequest request) {
        return usuarioResponseMapper.toResponse(usuarioService.adicionar(usuarioRequestMapper.toDomain(request)));
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponse> buscar(final @PathVariable("usuarioId") Long id) {
        return ResponseEntity.ok(usuarioResponseMapper.toResponse(usuarioService.buscar(id)));
    }

    @GetMapping
    public List<UsuarioListResponse> listar() {
        return usuarioResponseMapper.toCollectionModel(usuarioService.listar());
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<UsuarioResponse> atualizar(
            final @PathVariable("usuarioId") Long id,
            @Valid
            @RequestBody UsuarioComSenhaRequest request
    ) {
        var usuarioAtual = usuarioService.buscar(id);
        usuarioRequestMapper.copyToDomainObject(request, usuarioAtual);
        return ResponseEntity.ok(usuarioResponseMapper.toResponse(usuarioService.atualizar(usuarioAtual)));
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void alterarSenha(final @PathVariable Long usuarioId, @Valid @RequestBody SenhaRequest request) {
        usuarioService.alterarSenha(usuarioId, request.getSenhaAtual(), request.getNovaSenha());
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<?> remover(final @PathVariable("usuarioId") Long id) {
        usuarioService.remover(id);
        return ResponseEntity.noContent().build();
    }
}
