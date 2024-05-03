package br.com.mcm.apimcmfood.application.service;

import br.com.mcm.apimcmfood.domain.entity.Estado;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.infrastructure.repository.EstadoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class EstadoService {

    private EstadoRepository estadoRepository;

    public EstadoService(final EstadoRepository estadoRepository) {
        this.estadoRepository = Objects.requireNonNull(estadoRepository);
    }

    public Estado adicionar(final Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado buscar(final Long id) {
        return estadoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Estado com o codigo %d não foi econtrado na base de dados", id)
                ));
    }

    public Page<Estado> listar(final Pageable pageable) {
        return estadoRepository.findAll(pageable);
    }

    public Estado atualizar(final Long id, final Estado estado) {
        var estadoAtual = estadoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Estado com o codigo %d não foi econtrado na base de dados.", id)
                ));
        BeanUtils.copyProperties(estado, estadoAtual, "id");
        return this.estadoRepository.save(estadoAtual);
    }

    public Estado atualizarParcial(final Long id, final Estado estado) {
        var estadoAtual = estadoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format("Estado com o codigo %d não foi econtrado na base de dados.", id)
                ));
        BeanUtils.copyProperties(estado, estadoAtual, "id");
        return this.estadoRepository.save(estadoAtual);
    }

    public void remover(final Long id) {
        try {
            if (this.estadoRepository.existsById(id)) {
                this.estadoRepository.deleteById(id);
            } else {
                throw new EntidadeNaoEncontradaException(
                        String.format("Estado com o codigo %d não foi econtrado na base de dados.", id));
            }
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format("Não foi possível remover o estado com o código %d na base de dados devido à associação com uma ou mais cidades.", id));

        }
    }
}
