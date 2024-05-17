package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeJaExisteException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.exception.ValidaSubClasseException;
import br.com.mcm.apimcmfood.domain.exception.handler.ErroPadrao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErroPadrao> entidadeNaoEncontradaException(final EntidadeNaoEncontradaException e) {
        ErroPadrao erroPadrao = new ErroPadrao(
                HttpStatus.NOT_FOUND.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erroPadrao);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<ErroPadrao> entidadeEmUsoException(final EntidadeEmUsoException e) {
        ErroPadrao erroPadrao = new ErroPadrao(
                HttpStatus.CONFLICT.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erroPadrao);
    }

    @ExceptionHandler(ValidaSubClasseException.class)
    public ResponseEntity<ErroPadrao> subClasseExiste(final ValidaSubClasseException e) {
        ErroPadrao erroPadrao = new ErroPadrao(
                HttpStatus.BAD_REQUEST.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erroPadrao);
    }

    @ExceptionHandler(EntidadeJaExisteException.class)
    public ResponseEntity<ErroPadrao> entidadeJaExisteException(final EntidadeJaExisteException e) {
        ErroPadrao erroPadrao = new ErroPadrao(
                HttpStatus.CONFLICT.value(), e.getMessage());

        return ResponseEntity.status(HttpStatus.CONFLICT).body(erroPadrao);
    }
}
