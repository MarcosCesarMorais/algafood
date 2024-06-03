package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.api.model.grupo.mapper.GrupoRequestMapper;
import br.com.mcm.apimcmfood.api.model.grupo.mapper.GrupoResponseMapper;
import br.com.mcm.apimcmfood.api.model.permissao.PermissaoListResponse;
import br.com.mcm.apimcmfood.api.model.permissao.mapper.PermissaoRequestMapper;
import br.com.mcm.apimcmfood.api.model.permissao.mapper.PermissaoResponseMapper;
import br.com.mcm.apimcmfood.domain.entity.Grupo;
import br.com.mcm.apimcmfood.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/grupos/{grupoId}/permissao")
public class GrupoPermissaoController {
    @Autowired
    private GrupoRequestMapper grupoRequestMapper;
    @Autowired
    private GrupoResponseMapper grupoResponseMapper;
    @Autowired
    private PermissaoRequestMapper permissaoRequestMapper;
    @Autowired
    private PermissaoResponseMapper permissaoResponseMapper;

    private GrupoService grupoService;

    public GrupoPermissaoController(final GrupoService grupoService) {
        this.grupoService = Objects.requireNonNull(grupoService);
    }

    @GetMapping
    public List<PermissaoListResponse> listar(final @PathVariable("grupoId") Long id) {
        Grupo grupo = grupoService.buscar(id);
        return permissaoResponseMapper.toCollectionResponse(grupo.getPermissoes());
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associarPermissao(
            final @PathVariable("grupoId") Long grupoId,
            final @PathVariable("permissaoId") Long permissaoId
    ) {
        grupoService.associarPermissao(grupoId, permissaoId);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desassociarPermissao(
            final @PathVariable("grupoId") Long grupoId,
            final @PathVariable("permissaoId") Long permissaoId
    ) {
        grupoService.desassociarPermissao(grupoId, permissaoId);
    }
}
