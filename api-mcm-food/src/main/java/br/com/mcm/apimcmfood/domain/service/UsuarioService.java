package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.entity.Grupo;
import br.com.mcm.apimcmfood.domain.entity.Usuario;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.exception.NegocioException;
import br.com.mcm.apimcmfood.infrastructure.repository.UsuarioRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class UsuarioService {

    final static String MSG_USUARIO_NAO_ENCONTRADO
            = "Não existe um cadastro de usuário com código %d";

    private UsuarioRepository usuarioRepository;
    private GrupoService grupoService;


    public UsuarioService(
            final UsuarioRepository usuarioRepository,
            final GrupoService grupoService
    ) {
        this.usuarioRepository = Objects.requireNonNull(usuarioRepository);
        this.grupoService = Objects.requireNonNull(grupoService);
    }

    public Usuario adicionar(final Usuario usuario) {
        return salvar(usuario);
    }

    public Usuario buscar(final Long id) {
        return buscarOuFalhar(id);
    }

    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }

    public Usuario atualizar(final Usuario usuario) {
        return salvar(usuario);
    }

    public void remover(final Long id) {
        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_USUARIO_NAO_ENCONTRADO, id));
        } catch (
                DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_USUARIO_NAO_ENCONTRADO, id));
        }
    }

    @Transactional
    public void alterarSenha(Long usuarioId, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(usuarioId);

        if (usuario.senhaNaoCoincideCom(senhaAtual)) {
            throw new NegocioException("Senha atual informada não coincide com a senha do usuário.");
        }

        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void associarGrupo(final Long usuariId, final Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuariId);
        Grupo grupo = grupoService.buscar(grupoId);

        usuario.associarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(final Long usuarioId, final Long grupoId) {
        Usuario usuario = buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscar(grupoId);

        usuario.desassociarGrupo(grupo);
    }

    private Usuario buscarOuFalhar(final Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_USUARIO_NAO_ENCONTRADO, id))
        );
    }

    private Usuario salvar(final Usuario usuario) {
        usuarioRepository.detach(usuario);
        var usuarioExistente = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(
                    String.format("já existe um usuário cadastrado com o e-mail %s", usuario.getEmail())
            );
        }
        return usuarioRepository.save(usuario);
    }
}
