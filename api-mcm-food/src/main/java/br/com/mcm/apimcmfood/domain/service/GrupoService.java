package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.entity.Grupo;
import br.com.mcm.apimcmfood.domain.entity.Permissao;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.infrastructure.repository.GrupoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class GrupoService {

    public static String MSG_GRUPO_NAO_ENCONTRADO =
            "Não existe um cadastro de grupo com código %d.";

    private GrupoRepository grupoRepository;

    private PermissaoService permissaoService;


    public GrupoService(
            final GrupoRepository grupoRepository,
            final PermissaoService permissaoService
    ) {
        this.grupoRepository = Objects.requireNonNull(grupoRepository);
        this.permissaoService = Objects.requireNonNull(permissaoService);
    }

    public Grupo adicionar(final Grupo grupo) {
        return salvar(grupo);
    }

    public Grupo buscar(final Long id) {
        return buscarOuFalhar(id);
    }

    public List<Grupo> listar() {
        return grupoRepository.findAll();
    }

    public Grupo atualizar(final Grupo grupo) {
        return salvar(grupo);
    }

    public void remover(final Long id) {
        grupoRepository.deleteById(id);
    }

    @Transactional
    public void associarPermissao(final Long grupoId, final Long permissaoId) {
        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = permissaoService.buscar(permissaoId);

        grupo.associarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(final Long grupoId, final Long permissaoId) {
        Grupo grupo = buscarOuFalhar(grupoId);
        Permissao permissao = permissaoService.buscar(permissaoId);

        grupo.desassociarPermissao(permissao);
    }

    private Grupo buscarOuFalhar(Long id) {
        return grupoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_GRUPO_NAO_ENCONTRADO, id))
        );
    }

    private Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }
}
