package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.entity.FormaPagamento;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.infrastructure.repository.FormaPagamentoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class FormaPagamentoService {

    public static String MSG_FORMA_PAGAMENTO_NAO_ENCONTRADO =
            "Não existe um cadastro de forma de pagamento com código %d.";

    private static final String MSG_FORMA_PAGAMENTO_EM_USO =
            "Não é possível remover a forma de pagamento com o código %d, pois está associada a um Restaurante.";

    private FormaPagamentoRepository formaPagamentoRepository;

    public FormaPagamentoService(FormaPagamentoRepository formaPagamentoRepository) {
        this.formaPagamentoRepository = Objects.requireNonNull(formaPagamentoRepository);
    }

    public FormaPagamento adicionar(final FormaPagamento formaPagamento) {
        return salvar(formaPagamento);
    }

    public FormaPagamento buscar(final Long id) {
        return buscarOuFalhar(id);
    }

    public List<FormaPagamento> listar() {
        return formaPagamentoRepository.findAll();
    }

    public FormaPagamento atualizar(final FormaPagamento formaPagamento) {
        return salvar(formaPagamento);
    }

    public void remover(final Long id) {
        try {
            formaPagamentoRepository.deleteById(id);
            formaPagamentoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADO, id)
            );
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_FORMA_PAGAMENTO_EM_USO, id)
            );
        }
    }

    private FormaPagamento buscarOuFalhar(final Long id) {
        return formaPagamentoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_FORMA_PAGAMENTO_NAO_ENCONTRADO, id))
        );
    }

    private FormaPagamento salvar(FormaPagamento formaPagamento) {
        return formaPagamentoRepository.save(formaPagamento);
    }
}
