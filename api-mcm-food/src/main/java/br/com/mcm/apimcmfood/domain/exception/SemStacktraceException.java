package br.com.mcm.apimcmfood.domain.exception;

public class SemStacktraceException extends RuntimeException {

    public SemStacktraceException(final String message) {
        this(message, null);
    }

    public SemStacktraceException(final String message, final Throwable cause) {
        super(message, cause, true, false);
    }
}

