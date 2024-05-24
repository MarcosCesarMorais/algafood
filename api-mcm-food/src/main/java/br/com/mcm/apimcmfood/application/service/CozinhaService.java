package br.com.mcm.apimcmfood.application.service;

import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.entity.Cozinha;
import br.com.mcm.apimcmfood.infrastructure.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class CozinhaService {

    private CozinhaRepository cozinhaRepository;

    private static final String MSG_COZINHA_NAO_ENCONTRADA =
            "Não existe um cadastro de cozinha com código %d";
    private static final String MSG_COZINHA_EM_USO  =
            "Não é possível remover a cozinha com o código %d, pois está associada a um ou mais restaurantes.";


    public CozinhaService(
            final CozinhaRepository cozinhaRepository
    ) {
        this.cozinhaRepository = Objects.requireNonNull(cozinhaRepository);
    }
    @Transactional
    public Cozinha adicionar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public Cozinha buscar(Long id) {
        return cozinhaRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_COZINHA_NAO_ENCONTRADA, id))
        );
    }
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @Transactional
    public Cozinha atualizar(Long id, Cozinha cozinha) {
        var cozinhaAtual = cozinhaRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_COZINHA_NAO_ENCONTRADA, id)));
        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        return this.cozinhaRepository.save(cozinhaAtual);
    }

    @Transactional
    public void remover(final Long id) {
        try {
            if (this.cozinhaRepository.existsById(id)) {
                this.cozinhaRepository.deleteById(id);
            } else {
                throw new EntidadeNaoEncontradaException(
                        String.format(MSG_COZINHA_NAO_ENCONTRADA, id));
            }
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_COZINHA_EM_USO, id));
        }
    }
}
