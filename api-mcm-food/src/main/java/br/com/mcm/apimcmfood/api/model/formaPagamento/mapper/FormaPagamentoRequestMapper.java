package br.com.mcm.apimcmfood.api.model.formaPagamento.mapper;

import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoRequest;
import br.com.mcm.apimcmfood.domain.entity.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Component
public class FormaPagamentoRequestMapper {

    @Autowired
    private ModelMapper modelMapper;

    public FormaPagamento toDomain(FormaPagamentoRequest request) {
        return modelMapper.map(request, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoRequest request, FormaPagamento formaPagamento) {
        modelMapper.map(request, formaPagamento);
    }

}
