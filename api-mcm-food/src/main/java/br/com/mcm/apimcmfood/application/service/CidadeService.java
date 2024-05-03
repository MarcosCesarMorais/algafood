package br.com.mcm.apimcmfood.application.service;

import br.com.mcm.apimcmfood.domain.entity.Cidade;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.infrastructure.repository.CidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CidadeService {

    private CidadeRepository cidadeRepository;

    public CidadeService(final CidadeRepository cidadeRepository) {
        this.cidadeRepository = Objects.requireNonNull(cidadeRepository);
    }

    public Cidade adicionar(final Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public Cidade buscar(final Long id) {
        return cidadeRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Cidade do código %d não foi encontrada na base de dados", id))
        );
    }

    public Page<Cidade> listar(final Pageable pageable){
        return cidadeRepository.findAll(pageable);
    }

    public Cidade atualizar (final Long id, final Cidade cidade){
        var cidadeAtual = cidadeRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Cidade do código %d não foi encontrada na base de dados", id))
        );
        BeanUtils.copyProperties(cidade, cidadeAtual,"id");
        return this.cidadeRepository.save(cidadeAtual);
    }

    public void remover(final Long id){
        try{
            if (this.cidadeRepository.existsById(id)){
                this.cidadeRepository.deleteById(id);
            } else {
                throw new EntidadeNaoEncontradaException(
                        String.format("Não foi possível encontrar uma cidade com o código %d na base de dados.", id));
            }
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format("Não é possível remover a cidade com o código %d devido à associação com um estado.", id));
        }
    }
}
