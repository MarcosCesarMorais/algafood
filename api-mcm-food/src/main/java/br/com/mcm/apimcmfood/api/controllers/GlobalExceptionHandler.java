package br.com.mcm.apimcmfood.api.controllers;

import br.com.mcm.apimcmfood.domain.exception.handler.ErroPadraoType;
import br.com.mcm.apimcmfood.domain.exception.*;
import br.com.mcm.apimcmfood.domain.exception.handler.ErroPadrao;
import br.com.mcm.apimcmfood.domain.exception.handler.Field;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.NestedRuntimeException;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @Autowired
    private MessageSource messageSource;


    protected ResponseEntity<Object> handleTransactionSystem(
            NestedRuntimeException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ErroPadraoType type = ErroPadraoType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        List<Field> fields = null;
        String message = ex.getMessage();

        var erroPadrao = instanciaErroPadrao(status, type, message, fields);
        return handleExceptionInternal(ex, erroPadrao, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {

        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status,
                                                         WebRequest request) {

        return handleValidationInternal(ex, headers, status, request, ex.getBindingResult());
    }

    private ResponseEntity<Object> handleValidationInternal(
            Exception ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request,
            BindingResult bindingResult
    ) {
        ErroPadraoType type = ErroPadraoType.DADOS_INVALIDOS;
        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";
        List<Field> fields = bindingResult.getAllErrors().stream()
                .map(fieldError -> {
                    String message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale());
                    String name = fieldError.getObjectName();
                    if (fieldError instanceof FieldError) {
                        name = ((FieldError) fieldError).getField();
                    }
                    return new Field(
                            name,
                            message);
                })
                .collect(Collectors.toList());
        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);
        return handleExceptionInternal(ex, erroPadrao, headers, status, request);
    }

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
        List<Field> fields = null;
        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);
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

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<?> entidadeNaoEncontradaException(
            final EntidadeNaoEncontradaException ex,
            WebRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroPadraoType type = ErroPadraoType.RECURSO_NAO_ENCONTRADO;
        String detail = ex.getMessage();
        List<Field> fields = null;
        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);

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
        List<Field> fields = null;
        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);

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
        List<Field> fields = null;
        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);

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
        List<Field> fields = null;

        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);
        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request
    ) {
        ErroPadraoType type = ErroPadraoType.PARAMETRO_INVALIDO;
        String detail = String.format("O parâmetro de URL '%s' recebeu o valor '%s', "
                        + "que é de um tipo inválido. Corrija e informe um valor compatível com o tipo %s.",
                ex.getName(), ex.getValue(), ex.getRequiredType().getSimpleName());
        List<Field> fields = null;
        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);
        return handleExceptionInternal(ex, erroPadrao, headers, status, request);
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
        List<Field> fields = null;
        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);
        return handleExceptionInternal(ex, erroPadrao, headers, status, request);


    }

    protected ResponseEntity<Object> handlePropertyReferenceException(
            PropertyReferenceException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request
    ) {
        ErroPadraoType type = ErroPadraoType.MENSAGEM_INCOMPREENSIVEL;
        String detail = String.format("A propriedade '%s' não existe. "
                + "Corrija ou remova essa propriedade e tente novamente.", ex.getPropertyName());
        List<Field> fields = null;
        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);
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
        List<Field> fields = null;

        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);
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
        List<Field> fields = null;
        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);

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
        List<Field> fields = null;
        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);
        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(
            Exception ex,
            WebRequest request
    ) {

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErroPadraoType type = ErroPadraoType.ERRO_DE_SISTEMA;
        String detail = "Ocorreu um erro interno inesperado no sistema. "
                + "Tente novamente e se o problema persistir, entre em contato "
                + "com o administrador do sistema.";
        List<Field> fields = null;
        ex.printStackTrace();
        var erroPadrao = instanciaErroPadrao(status, type, detail, fields);
        return handleExceptionInternal(ex, erroPadrao, new HttpHeaders(), status, request);
    }

    private ErroPadrao instanciaErroPadrao(HttpStatus status, ErroPadraoType type, String detail, List<Field> fields) {
        return new ErroPadrao(
                status.value(),
                type.getUri(),
                type.getTitle(),
                detail,
                fields
        );
    }

    private String joinPath(List<Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }
}
