package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.entity.Cidade;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeJaExisteException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.exception.NegocioException;
import br.com.mcm.apimcmfood.infrastructure.repository.CidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CidadeService {
    private static final String MSG_CIDADE_NAO_ENCONTRADA =
            "Não existe um cadastro de cidade com código %d.";
    private static final String MSG_ESTADO_NAO_ENCONTRADA =
            "Não existe um cadastro de estado com código %d.";
    private static final String MSG_CIDADE_EM_USO  =
            "Não é possível remover a cidade com o código %d, pois está associada a um Estado.";

    private CidadeRepository cidadeRepository;
    private EstadoService estadoService;

    public CidadeService(
            final CidadeRepository cidadeRepository,
            final EstadoService estadoService
    ) {
        this.cidadeRepository = Objects.requireNonNull(cidadeRepository);
        this.estadoService = Objects.requireNonNull(estadoService);
    }

    public Cidade adicionar(final Cidade cidade) {
        var cidadeAtual = cidadeRepository.findByNomeContainingAndEstadoId(cidade.getNome(),cidade.getEstado().getId());

        if(cidadeAtual.isPresent()){
                throw new EntidadeJaExisteException(
                        String.format("Cidade com o nome " + cidade.getNome() +", já existe no Estado "+ cidadeAtual.get().getEstado().getNome() +"."));
        }
        return cidadeRepository.save(cidade);
    }

    public Cidade buscar(final Long id) {
        return cidadeRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_CIDADE_NAO_ENCONTRADA, id))
        );
    }

    public List<Cidade> listar(){
        return cidadeRepository.findAll();
    }

    public Cidade atualizar (final Long id, final Cidade cidade){
        var cidadeAtual = cidadeRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_CIDADE_NAO_ENCONTRADA, id))
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
                        String.format(MSG_CIDADE_NAO_ENCONTRADA, id));
            }
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(
                    String.format(MSG_CIDADE_EM_USO, id));
        }
    }
}
