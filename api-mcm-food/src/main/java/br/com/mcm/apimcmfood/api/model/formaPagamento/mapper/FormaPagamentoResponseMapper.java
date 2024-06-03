package br.com.mcm.apimcmfood.api.model.formaPagamento.mapper;

import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoListResponse;
import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoResponse;
import br.com.mcm.apimcmfood.domain.entity.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoResponseMapper {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamentoResponse toResponse(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoResponse.class);
    }

    public FormaPagamentoListResponse toListResponse(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoListResponse.class);
    }

    public List<FormaPagamentoListResponse> toCollectionResponse(Collection<FormaPagamento> formaPagamentos) {
        return formaPagamentos
                .stream()
                .map(formaPagamento -> toListResponse(formaPagamento))
                .collect(Collectors.toList());
    }
}
