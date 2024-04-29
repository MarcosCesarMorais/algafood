package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.model.Cozinha;
import br.com.mcm.apimcmfood.domain.repository.CozinhaRepository;
import org.springframework.stereotype.Service;

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


    public void remover(final Long id) {
        cozinhaRepository.deleteById(id);
    }
}
