package br.com.mcm.apimcmfood.domain.service;

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


    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = Objects.requireNonNull(usuarioRepository);
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

    private Usuario buscarOuFalhar(final Long id) {
        return usuarioRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_USUARIO_NAO_ENCONTRADO, id))
        );
    }

    private Usuario salvar(final Usuario usuario) {
        return usuarioRepository.save(usuario);
    }
}
