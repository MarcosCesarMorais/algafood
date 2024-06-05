package br.com.mcm.apimcmfood.domain.service;

import br.com.mcm.apimcmfood.domain.entity.Pedido;
import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.infrastructure.repository.PedidoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
@Service
public class PedidoService {

    private PedidoRepository pedidoRepository;

    private static final String MSG_PEDIDO_EM_USO  =
            "Pedido de código %d não pode ser removido, pois está em uso";

    private static final String MSG_PEDIDO_NAO_ENCONTRADO =
            "Não existe um cadastro de PedidoRequest com código %d";

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public Pedido adicionar(Pedido pedido){
        return pedidoRepository.save(pedido);
    }

    public Pedido buscar(Long id) {
        return pedidoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_PEDIDO_NAO_ENCONTRADO, id))
        );
    }

    public List<Pedido> listar() {
        return pedidoRepository.findAll();
    }

    public Pedido atualizar(Long id, Pedido pedido) {
        var pedidoAtual = pedidoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_PEDIDO_NAO_ENCONTRADO, id)));
        BeanUtils.copyProperties(pedido, pedidoAtual, "id");
        return this.pedidoRepository.save(pedidoAtual);
    }

    public Pedido atualizarParcial(Long id, Map<String, Object> campos){
        var pedidoAtual = pedidoRepository.findById(id).orElseThrow(
                () -> new EntidadeNaoEncontradaException(
                        String.format(MSG_PEDIDO_NAO_ENCONTRADO, id)));
        merge(campos, pedidoAtual);
        return this.pedidoRepository.save(pedidoAtual);
    }

    public void remover(final Long id) {
        try {
            if (this.pedidoRepository.existsById(id)) {
                this.pedidoRepository.deleteById(id);
            } else {
                throw new EntidadeNaoEncontradaException(
                        String.format(MSG_PEDIDO_NAO_ENCONTRADO, id));
            }
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_PEDIDO_EM_USO, id));
        }
    }

    private void merge(Map<String, Object> dadosOrigem, Pedido pedidoDestino){
        ObjectMapper objectMapper = new ObjectMapper();
        Pedido pedidoOrigem = objectMapper.convertValue(dadosOrigem, Pedido.class);
        dadosOrigem.forEach((nomePropriedade, valorPropriedade)->{
            Field field = ReflectionUtils.findField(Pedido.class, nomePropriedade);
            field.setAccessible(true);
            Object valorAtualizado = ReflectionUtils.getField(field, pedidoOrigem);
            ReflectionUtils.setField(field, pedidoDestino, valorAtualizado);
        });
    }
}
