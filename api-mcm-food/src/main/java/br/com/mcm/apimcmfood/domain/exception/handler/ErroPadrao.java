package br.com.mcm.apimcmfood.domain.exception.handler;

public record ErroPadrao(
        Integer status,
        String error
) {
}
