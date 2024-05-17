package br.com.mcm.apimcmfood.domain.exception;

public class EntidadeJaExisteException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    public EntidadeJaExisteException(String mensagem){
        super(mensagem);
    }
}
