package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.api.model.grupo.GrupoListResponse;
import br.com.mcm.apimcmfood.api.model.grupo.mapper.GrupoResponseMapper;
import br.com.mcm.apimcmfood.domain.entity.Usuario;
import br.com.mcm.apimcmfood.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private GrupoResponseMapper grupoResponseMapper;

    private UsuarioService usuarioService;

    public UsuarioGrupoController(UsuarioService usuarioService) {
        this.usuarioService = Objects.requireNonNull(usuarioService);
    }

    @GetMapping
    public List<GrupoListResponse> listarGrupos(final @PathVariable("usuarioId") Long id) {
        Usuario usuario = usuarioService.buscar(id);
        return grupoResponseMapper.toCollectionResponse(usuario.getGrupos());
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarGrupo(
            final @PathVariable("usuarioId") Long usuarioId,
            final @PathVariable("grupoId") Long grupoId
    ) {
        usuarioService.associarGrupo(usuarioId, grupoId);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarGrupo(
            final @PathVariable("usuarioId") Long usuarioId,
            final @PathVariable("grupoId") Long grupoId
    ) {
        usuarioService.desassociarGrupo(usuarioId, grupoId);
    }
}
