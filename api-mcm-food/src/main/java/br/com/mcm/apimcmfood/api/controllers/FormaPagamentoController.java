package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoListResponse;
import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoRequest;
import br.com.mcm.apimcmfood.api.model.formaPagamento.FormaPagamentoResponse;
import br.com.mcm.apimcmfood.api.model.formaPagamento.mapper.FormaPagamentoRequestMapper;
import br.com.mcm.apimcmfood.api.model.formaPagamento.mapper.FormaPagamentoResponseMapper;
import br.com.mcm.apimcmfood.domain.entity.FormaPagamento;
import br.com.mcm.apimcmfood.domain.service.FormaPagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/formaPagamento")
public class FormaPagamentoController {

    @Autowired
    private FormaPagamentoRequestMapper formaPagamentoRequestMapper;

    @Autowired
    private FormaPagamentoResponseMapper formaPagamentoResponseMapper;

    private FormaPagamentoService formaPagamentoService;


    public FormaPagamentoController(FormaPagamentoService formaPagamentoService) {
        this.formaPagamentoService = Objects.requireNonNull(formaPagamentoService);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoResponse adicionar(final @Valid @RequestBody FormaPagamentoRequest request) {
        return formaPagamentoResponseMapper.toResponse(formaPagamentoService.adicionar(formaPagamentoRequestMapper.toDomain(request)));
    }

    @GetMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoResponse> buscar(final @PathVariable("formaPagamentoId") Long id) {
        return ResponseEntity.ok(
                formaPagamentoResponseMapper.toResponse(formaPagamentoService.buscar(id))
        );
    }

    @GetMapping
    public List<FormaPagamentoListResponse> listar() {
        return formaPagamentoResponseMapper.toCollectionResponse(formaPagamentoService.listar());
    }

    @PutMapping("/{formaPagamentoId}")
    public ResponseEntity<FormaPagamentoResponse> atualizar(final @PathVariable("formaPagamentoId") Long id,
                                                            final @Valid @RequestBody FormaPagamentoRequest request
    ) {
        FormaPagamento formaPagamentoAtual = formaPagamentoService.buscar(id);
        formaPagamentoRequestMapper.copyToDomainObject(request, formaPagamentoAtual);

        return ResponseEntity.ok(formaPagamentoResponseMapper.toResponse(formaPagamentoService.atualizar(formaPagamentoAtual)));
    }

    @DeleteMapping("/{formaPagamentoId}")
    public ResponseEntity<?> remover(final @PathVariable("formaPagamentoId") Long id) {
        formaPagamentoService.remover(id);
        return ResponseEntity.noContent().build();
    }

}
