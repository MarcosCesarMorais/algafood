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

    private static final String MSG_ESTADO_EM_USO  =
            "Estado de código %d não pode ser removido, pois está em uso";

    private static final String MSG_ESTADO_NAO_ENCONTRADO =
            "Não existe um cadastro de estado com código %d";

    public EstadoService(final EstadoRepository estadoRepository) {
        this.estadoRepository = Objects.requireNonNull(estadoRepository);
    }

    public Estado adicionar(final Estado estado) {
        return estadoRepository.save(estado);
    }

    public Estado buscar(final Long id) {
        return estadoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_ESTADO_NAO_ENCONTRADO, id)
                ));
    }

    public Page<Estado> listar(final Pageable pageable) {
        return estadoRepository.findAll(pageable);
    }

    public Estado atualizar(final Long id, final Estado estado) {
        var estadoAtual = estadoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_ESTADO_NAO_ENCONTRADO, id)
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
                        String.format(MSG_ESTADO_NAO_ENCONTRADO, id));
            }
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, id));

        }
    }

    public Boolean existe(Long id){
        return this.estadoRepository.existsById(id);
    }
}
