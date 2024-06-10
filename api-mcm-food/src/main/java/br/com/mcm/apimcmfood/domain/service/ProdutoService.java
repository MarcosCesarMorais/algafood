package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.entity.Produto;
import br.com.mcm.apimcmfood.domain.entity.Restaurante;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.exception.ProdutoNaoEncontradaException;
import br.com.mcm.apimcmfood.infrastructure.repository.ProdutoRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class ProdutoService {

    private static final String MSG_PRODUTO_EM_USO =
            "Produto de código %d não pode ser removido, pois está em uso";

    private static final String MSG_PRODUTO_NAO_ENCONTRADO =
            "Não existe um produto com código %d";

    private ProdutoRepository produtoRepository;

    public ProdutoService(final ProdutoRepository produtoRepository) {
        this.produtoRepository = Objects.requireNonNull(produtoRepository);
    }

    @Transactional
    public Produto adicionar(final Produto produto) {
        return produtoRepository.save(produto);
    }

    public List<Produto> listar() {
        return produtoRepository.findAll();
    }

    public void remover(final Long id) {
        try {
            if (produtoRepository.existsById(id)) {
                produtoRepository.deleteById(id);
            } else {
                throw new EntidadeNaoEncontradaException(
                        String.format(MSG_PRODUTO_NAO_ENCONTRADO, id));
            }
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PRODUTO_EM_USO, id));
        }
    }

    public List<Produto> findTodosByRestaurante(final Restaurante restaurante) {
        return produtoRepository.findByRestaurante(restaurante);
    }

    public List<Produto> findAtivosByRestaurante(final Restaurante restaurante) {
        return produtoRepository.findAtivosByRestaurante(restaurante);
    }

    public Produto buscarOuFalhar(final Long restauranteId, final Long produtoId) {
        return produtoRepository.findById(restauranteId, produtoId).orElseThrow(
                () -> new ProdutoNaoEncontradaException(restauranteId, produtoId));
    }
}
