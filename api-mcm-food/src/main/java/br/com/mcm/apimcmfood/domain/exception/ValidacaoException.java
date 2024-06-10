package br.com.mcm.apimcmfood.domain.exception;

import org.springframework.validation.BindingResult;

public class ValidacaoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private BindingResult bindingResult;

    public ValidacaoException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
    }

    public BindingResult getBindingResult() {
        return bindingResult;
    }
}
