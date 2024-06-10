package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.entity.Permissao;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.infrastructure.repository.PermissaoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class PermissaoService {

    final static String MSG_PERMISSAO_NAO_ENCONTRADA
            = "Não existe uma permissão com código %d";

    final static String MSG_PERMISSAO_EM_USO
            = "Permissão em uso %d";

    private PermissaoRepository permissaoRepository;

    public PermissaoService(PermissaoRepository permissaoRepository) {
        this.permissaoRepository = Objects.requireNonNull(permissaoRepository);
    }

    public Permissao adicionar(final Permissao permissao) {
        return salvar(permissao);
    }

    public Permissao buscar(final Long id) {
        return buscarOuFalhar(id);
    }

    public List<Permissao> listar() {
        return permissaoRepository.findAll();
    }

    public Permissao atualizar(final Permissao permissao) {
        return salvar(permissao);
    }

    public void remover(final Long id) {
        try {
            permissaoRepository.deleteById(id);
            permissaoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_PERMISSAO_NAO_ENCONTRADA, id));
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PERMISSAO_EM_USO, id));
        }
    }

    private Permissao salvar(final Permissao permissao) {
        return permissaoRepository.save(permissao);
    }

    private Permissao buscarOuFalhar(final Long id) {
        return permissaoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_PERMISSAO_NAO_ENCONTRADA, id))
        );
    }
}
