package br.com.mcm.apimcmfood.domain.exception;

public class EntidadeNaoEncontradaException extends SemStacktraceException{

    private static final long serialVersionUID = 1L;

    public EntidadeNaoEncontradaException(String mensagem){
        super(mensagem);
    }
}
