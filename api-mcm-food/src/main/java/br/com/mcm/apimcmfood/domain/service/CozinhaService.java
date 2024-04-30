package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.model.Cozinha;
import br.com.mcm.apimcmfood.domain.repository.CozinhaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

@Service
public class CozinhaService {

    private CozinhaRepository cozinhaRepository;

    public CozinhaService(
            final CozinhaRepository cozinhaRepository
    ) {
        this.cozinhaRepository = Objects.requireNonNull(cozinhaRepository);
    }

    public Cozinha adicionar(Cozinha cozinha) {
        return cozinhaRepository.save(cozinha);
    }

    public Cozinha buscar(Long id) {
        return cozinhaRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Cozinha de código %d não foi encontrada na base de dados", id))
        );
    }

    public Page<Cozinha> listar(Pageable pageable) {
        return cozinhaRepository.findAll(pageable);
    }

    public Cozinha atualizar(Long id, Cozinha cozinha) {
        var cozinhaAtual = cozinhaRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Não foi possível encontrar uma cozinha com o código %d na base de dados.", id)));
        BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        return this.cozinhaRepository.save(cozinhaAtual);
    }


    public void remover(final Long id) {
        try {
            if (this.cozinhaRepository.existsById(id)) {
                this.cozinhaRepository.deleteById(id);
            } else {
                throw new EntidadeNaoEncontradaException(
                        String.format("Não foi possível encontrar uma cozinha com o código %d na base de dados.", id));
            }
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Não é possível remover a cozinha com o código %d devido à associação com um ou mais restaurantes.", id));
        }
    }
}
