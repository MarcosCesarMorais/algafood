package br.com.mcm.apimcmfood.api.execeptionHandler;

import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeJaExisteException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.exception.NegocioException;
import br.com.mcm.apimcmfood.domain.exception.handler.ErroPadrao;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if(rootCause instanceof InvalidFormatException){
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        }
        ErroPadraoType type = ErroPadraoType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
        var erroPadrao = instanciaErroPadrao(status, type, detail);
        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleInvalidFormatException(
            InvalidFormatException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        String path = ex.getPath().stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));

        ErroPadraoType type = ErroPadraoType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' recebeu o valor '%s'," +
                "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                path,ex.getValue(),ex.getTargetType().getSimpleName());

        var erroPadrao = instanciaErroPadrao(status, type, detail);
        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> entidadeNaoEncontradaException(
            final EntidadeNaoEncontradaException ex,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroPadraoType type = ErroPadraoType.ENTIDADE_NAO_ENCONTRADA;
        String detail = ex.getMessage();
        var erroPadrao = instanciaErroPadrao(status, type, detail);

        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeEmUsoException.class)
    public ResponseEntity<?> entidadeEmUsoException(
            final EntidadeEmUsoException ex,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErroPadraoType type = ErroPadraoType.ENTIDADE_EM_USO;
        String detail = ex.getMessage();
        var erroPadrao = instanciaErroPadrao(status, type, detail);

        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> negocioException(
            final NegocioException ex,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroPadraoType type = ErroPadraoType.ERRO_NEGOCIO;
        String detail = ex.getMessage();
        var erroPadrao = instanciaErroPadrao(status, type, detail);

        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(EntidadeJaExisteException.class)
    public ResponseEntity<?> entidadeJaExisteException(
            final EntidadeJaExisteException ex,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErroPadraoType type = ErroPadraoType.ENTIDADE_EM_USO;
        String detail = ex.getMessage();

        var erroPadrao = instanciaErroPadrao(status, type, detail);
        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @Override
    protected  ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ){
        if (body == null) {
             body =  new ErroPadrao(
                    status.value(),
                    status.getReasonPhrase()
            );
        } else if (body instanceof String){
             body = new ErroPadrao(
                    status.value(),
                    (String) body
            );
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ErroPadrao instanciaErroPadrao(HttpStatus status, ErroPadraoType type, String detail){
        return new ErroPadrao(
                status.value(),
                type.getUri(),
                type.getTitle(),
                detail
        );
    }
}
