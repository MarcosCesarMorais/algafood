package br.com.mcm.apimcmfood.api.execeptionHandler;

import br.com.mcm.apimcmfood.domain.exception.EntidadeEmUsoException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeJaExisteException;
import br.com.mcm.apimcmfood.domain.exception.EntidadeNaoEncontradaException;
import br.com.mcm.apimcmfood.domain.exception.NegocioException;
import br.com.mcm.apimcmfood.domain.exception.handler.ErroPadrao;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(
            TypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status, WebRequest request
    ) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch(
                    (MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status, WebRequest request
    ) {
        if (ex instanceof NoHandlerFoundException) {
            return handleNoHandlerFound(
                    (NoHandlerFoundException) ex, headers, status, request);
        }

        return super.handleNoHandlerFoundException(ex, headers, status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request
    ) {
        ErroPadraoType type = ErroPadraoType.PARAMETRO_INVALIDO;
        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());

        var erroPadrao = instanciaErroPadrao(status, type, detail);
        return handleExceptionInternal(ex, erroPadrao, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);
        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormatException((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, status, request);
        }
        ErroPadraoType type = ErroPadraoType.MENSAGEM_INCOMPREENSIVEL;
        String detail = "O corpo da requisição está inválido. Verifique erro de sintaxe.";
        var erroPadrao = instanciaErroPadrao(status, type, detail);
        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), status, request);
    }
    private ResponseEntity<Object> handlePropertyBindingException(
            PropertyBindingException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        String path = joinPath(ex.getPath());
        ErroPadraoType type = ErroPadraoType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", path);

        var erroPadrao = instanciaErroPadrao(status, type, detail);
        return handleExceptionInternal(ex, erroPadrao, headers, status, request);


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
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        var erroPadrao = instanciaErroPadrao(status, type, detail);
        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ErroPadraoType type = ErroPadraoType.PARAMETRO_INVALIDO;
        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s'," +
                        "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getParameter(), ex.getValue(), ex.getName());
        var erroPadrao = instanciaErroPadrao(status, type, detail);
        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), status, request);
    }

    private ResponseEntity<Object> handleNoHandlerFound(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ErroPadraoType type = ErroPadraoType.RECURSO_NAO_ENCONTRADO;
        String detail = String.format("O recurso '%s', que você tentou acessar, é inexistente.",
                ex.getRequestURL());
        var erroPadrao = instanciaErroPadrao(status, type, detail);
        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> entidadeNaoEncontradaException(
            final EntidadeNaoEncontradaException ex,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroPadraoType type = ErroPadraoType.RECURSO_NAO_ENCONTRADO;
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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object>handleUncaught(
            Exception ex,
            WebRequest request
    ){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErroPadraoType type = ErroPadraoType.ERRO_DE_SISTEMA;
        String detail = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";
        // Importante colocar o printStackTrace (pelo menos por enquanto, que não estamos
        // fazendo logging) para mostrar a stacktrace no console
        // Se não fizer isso, você não vai ver a stacktrace de exceptions que seriam importantes
        // para você durante, especialmente na fase de desenvolvimento
        ex.printStackTrace();
        var erroPadrao = instanciaErroPadrao(status, type, detail);
        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), status, request);
    }


    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        if (body == null) {
            body = new ErroPadrao(
                    status.value(),
                    status.getReasonPhrase()
            );
        } else if (body instanceof String) {
            body = new ErroPadrao(
                    status.value(),
                    (String) body
            );
        }
        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    private ErroPadrao instanciaErroPadrao(HttpStatus status, ErroPadraoType type, String detail) {
        return new ErroPadrao(
                status.value(),
                type.getUri(),
                type.getTitle(),
                detail
        );
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }
}
