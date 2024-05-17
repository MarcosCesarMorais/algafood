package br.com.mcm.apimcmfood.domain.exception;

public class ValidaSubClasseException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public ValidaSubClasseException(String mensagem){
        super(mensagem);
    }
}
