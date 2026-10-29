package org.example.orgflow.exceptions.handler;

import org.example.orgflow.common.utils.ObjectChecker;
import org.example.orgflow.exceptions.model.ApiResponse;
import org.example.orgflow.exceptions.model.Error;
import org.example.orgflow.exceptions.model.ErrorPayload;
import org.example.orgflow.exceptions.model.*;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.server.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Iterator;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);
    @Autowired
    private MessageSource messageSource;

    public RestExceptionHandler() {
        super();
    }


    @Override
    protected Mono<ResponseEntity<Object>> handleWebExchangeBindException(final WebExchangeBindException ex, final HttpHeaders headers, final HttpStatusCode status, final ServerWebExchange request) {

        logger.error(ex.getMessage());
        ex.printStackTrace();
        StringBuilder stringBuilder = new StringBuilder();
        final List<Error> errors = new ArrayList<Error>();
        if (ex.getBindingResult() != null) {
            for (final FieldError error : ex.getBindingResult().getFieldErrors()) {
                if (stringBuilder.length() > 0)
                    stringBuilder.append(" , ");
                errors.add(new Error(error.getField() + ": " + (ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(error.getDefaultMessage(), null, new Locale("en"))) ? messageSource.getMessage(error.getDefaultMessage(), null, new Locale("en")) : error.getDefaultMessage()), ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(error.getDefaultMessage(), null, new Locale("ar"))) ? messageSource.getMessage(error.getDefaultMessage(), null, new Locale("ar")) : error.getDefaultMessage()));
                stringBuilder.append(error.getField());
                stringBuilder.append(": ");
                stringBuilder.append(error.getDefaultMessage());
            }
            for (final ObjectError error : ex.getBindingResult().getGlobalErrors()) {
                if (stringBuilder.length() > 0)
                    stringBuilder.append(" , ");
                errors.add(new Error(error.getObjectName() + ": " + (ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(error.getDefaultMessage(), null, new Locale("en"))) ? messageSource.getMessage(error.getDefaultMessage(), null, new Locale("en")) : error.getDefaultMessage()), ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(error.getDefaultMessage(), null, new Locale("ar"))) ? messageSource.getMessage(error.getDefaultMessage(), null, new Locale("ar")) : error.getDefaultMessage()));
                stringBuilder.append(error.getObjectName());
                stringBuilder.append(": ");
                stringBuilder.append(error.getDefaultMessage());
            }
        }
        final ErrorPayload errorPayload = new ErrorPayload(stringBuilder.toString(), errors);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return handleExceptionInternal(ex, apiResponse, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleMethodNotAllowedException(MethodNotAllowedException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getHttpMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        if (ex.getSupportedMethods() != null && !ex.getSupportedMethods().isEmpty()) {
            ex.getSupportedMethods().forEach(t -> builder.append(t + " "));
        }

        StringBuilder message = new StringBuilder();
        message.append("Request Method ");
        message.append(ex.getHttpMethod());
        message.append(" Not Supported");


        final ErrorPayload errorPayload = new ErrorPayload(message.toString(), builder.toString());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return handleExceptionInternal(ex, apiResponse, headers, HttpStatus.METHOD_NOT_ALLOWED, exchange);

    }

    @ExceptionHandler({TypeMismatchException.class})
    protected ResponseEntity<Object> handleTypeMismatch(final TypeMismatchException ex) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final String error = ex.getValue() + " value for " + ex.getPropertyName() + " should be of type " + ex.getRequiredType();

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), error);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler({MissingServletRequestPartException.class})
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException ex) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final String error = ex.getRequestPartName() + " part is missing";
        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), error);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({MissingServletRequestParameterException.class})
    protected ResponseEntity<Object> handleMissingServletRequestParameter(final MissingServletRequestParameterException ex) {

        ex.printStackTrace();
        final String error = ex.getParameterName() + " parameter is missing";
        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), error);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<Object> handleMethodArgumentTypeMismatch(final MethodArgumentTypeMismatchException ex) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final String error = ex.getName() + " should be of type " + ex.getRequiredType().getName();

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), error);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException ex) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final List<Error> errors = new ArrayList<Error>();
        for (final ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(new Error(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": " + violation.getMessage()));
        }

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), errors);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler({NoHandlerFoundException.class})
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();

        final String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), error);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({EntityNotFoundException.class})
    protected Mono<ResponseEntity<ApiResponse>> handleAdminNotFoundException(EntityNotFoundException ex) {
        logger.error(ex.getMessage());
        ex.printStackTrace();

        final String errorKey = ex.getMessage();

        String errorEn = messageSource.getMessage(errorKey, null, new Locale("en"));
        String errorAr = messageSource.getMessage(errorKey, null, new Locale("ar"));
        if (ObjectChecker.isEmptyOrNull(errorEn))
            errorEn = errorKey;
        else if (ObjectChecker.isNotEmptyOrNull(ex.getId()))
            errorEn = errorEn.formatted((ex.getId()));
        else errorEn = errorEn.formatted((""));

        if (ObjectChecker.isEmptyOrNull(errorAr))
            errorAr = errorKey;
        else if (ObjectChecker.isNotEmptyOrNull(ex.getId()))
            errorAr = errorAr.formatted((ex.getId()));
        else errorAr = errorAr.formatted((""));

        List<Error> errors = new ArrayList<>();
        errors.add(new Error(errorEn, errorAr));

        final ErrorPayload errorPayload = new ErrorPayload(errorEn, errorAr, errors);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(404);
        return Mono.just(new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND));
    }


    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(final HttpRequestMethodNotSupportedException ex) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getMethod());
        builder.append(" method is not supported for this request. Supported methods are ");
        if (ex.getSupportedHttpMethods() != null && !ex.getSupportedHttpMethods().isEmpty()) {
            ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));
        }
        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), builder.toString());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return new ResponseEntity<Object>(apiResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }



    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException ex, final HttpHeaders headers, final HttpStatusCode status, final WebRequest request) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        if (ex.getSupportedMediaTypes() != null && !ex.getSupportedMediaTypes().isEmpty()) {
            ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
        }

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return new ResponseEntity<Object>(apiResponse, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

    }

    @ExceptionHandler({BusinessException.class})
    public ResponseEntity<Object> handleBusinessException(final BusinessException ex) {

        logger.error(ex.getMessage());
        ex.printStackTrace();


        if (ex.getStatus() != HttpStatus.UNAUTHORIZED) {
            String messageEn = messageSource.getMessage(ex.getMessage(), null, new Locale("en"));
            String messageAr = messageSource.getMessage(ex.getMessage(), null, new Locale("ar"));

            if (ObjectChecker.isEmptyOrNull(messageEn))
                messageEn = ex.getMessage();

            if (ObjectChecker.isEmptyOrNull(messageAr))
                messageAr = ex.getMessage();

            List<Error> errors = new ArrayList<>();
            if (ObjectChecker.isNotEmptyOrNull(ex.getErrorKeys())) {
                ex.getErrorKeys().stream().forEach(s -> {
                    errors.add(new Error(ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(s, null, new Locale("en"))) ? messageSource.getMessage(s, null, new Locale("en")) : s, ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(s, null, new Locale("ar"))) ? messageSource.getMessage(s, null, new Locale("ar")) : s));
                });
            }
            if (errors.size() == 0)
                errors.add(new Error(ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(ex.getMessage(), null, new Locale("en"))) ? messageSource.getMessage(ex.getMessage(), null, new Locale("en")) : ex.getMessage(), ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(ex.getMessage(), null, new Locale("ar"))) ? messageSource.getMessage(ex.getMessage(), null, new Locale("ar")) : ex.getMessage()));

            final ErrorPayload errorPayload = new ErrorPayload(messageEn, messageAr, errors);
            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setErrors(errorPayload);
            apiResponse.setSuccess(false);
            apiResponse.setCode(400);
            return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);
        } else {
            String messageEn = ex.getMessage();
            String messageAr = ex.getMessage();

            try{
                messageEn = messageSource.getMessage(ex.getMessage(), null, new Locale("en"));
                messageAr = messageSource.getMessage(ex.getMessage(), null, new Locale("ar"));
            } catch (Exception e) {

            }

            final ErrorPayload errorPayload = new ErrorPayload(messageEn, messageAr, null);

            ApiResponse apiResponse = new ApiResponse();
            apiResponse.setErrors(errorPayload);
            apiResponse.setSuccess(false);
            apiResponse.setCode(ex.getStatus().value());
            return new ResponseEntity<Object>(apiResponse, ex.getStatus());
        }
    }



    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleException(final Exception ex) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), "error occurred");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return new ResponseEntity<Object>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    protected Mono<ResponseEntity<Object>> handleNotAcceptableStatusException(NotAcceptableStatusException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final StringBuilder builder = new StringBuilder();
        builder.append("Status is not acceptable");

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), builder.toString());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return this.handleExceptionInternal(ex, apiResponse, headers, status, exchange);
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleUnsupportedMediaTypeStatusException(UnsupportedMediaTypeStatusException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getContentType());
        builder.append(" media type is not supported. Supported media types are ");
        if (ex.getSupportedMediaTypes() != null && !ex.getSupportedMediaTypes().isEmpty()) {
            ex.getSupportedMediaTypes().forEach(t -> builder.append(t + " "));
        }

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), builder.substring(0, builder.length() - 2));
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return this.handleExceptionInternal(ex, apiResponse, headers, status, exchange);

    }

    @Override
    protected Mono<ResponseEntity<Object>> handleMissingRequestValueException(MissingRequestValueException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getName());
        builder.append(" value is missing");


        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), builder.toString());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return this.handleExceptionInternal(ex, apiResponse, headers, status, exchange);
    }

    @Override
    protected Mono<ResponseEntity<Object>> handleUnsatisfiedRequestParameterException(UnsatisfiedRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        List<Error> errors = new ArrayList<>();

        if (ex.getRequestParams() != null) {
            Iterator<String> it = ex.getRequestParams().keySet().iterator();

            while (it.hasNext()) {
                String theKey = (String) it.next();
                StringBuilder builder = new StringBuilder();
                builder.append(theKey);
                builder.append(" request parameter is not satisfied for value ");
                builder.append(ex.getRequestParams().getFirst(theKey));

                Error error = new Error(builder.toString());
                errors.add(error);
            }
        }

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), errors);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return this.handleExceptionInternal(ex, apiResponse, headers, status, exchange);

    }

    @Override
    protected Mono<ResponseEntity<Object>> handleServerWebInputException(ServerWebInputException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final StringBuilder builder = new StringBuilder();
        builder.append("Method ");
        builder.append(ex.getMethodParameter().getMethod());
        builder.append(" with parameter ");
        builder.append(ex.getMethodParameter().getParameterName());
        builder.append(" not valid");

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), builder.toString());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return this.handleExceptionInternal(ex, apiResponse, headers, status, exchange);

    }

    @Override
    protected Mono<ResponseEntity<Object>> handleResponseStatusException(ResponseStatusException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getReason());

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), builder.toString());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return this.handleExceptionInternal(ex, apiResponse, headers, status, exchange);


    }

    @Override
    protected Mono<ResponseEntity<Object>> handleServerErrorException(ServerErrorException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), "error occurred");
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return this.handleExceptionInternal(ex, apiResponse, headers, status, exchange);

    }

    @Override
    protected Mono<ResponseEntity<Object>> handleErrorResponseException(ErrorResponseException ex, HttpHeaders headers, HttpStatusCode status, ServerWebExchange exchange) {

        logger.error(ex.getMessage());
        ex.printStackTrace();

        final StringBuilder builder = new StringBuilder();
        builder.append(ex.getBody().getDetail());

        final ErrorPayload errorPayload = new ErrorPayload(ex.getLocalizedMessage(), builder.toString());
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setErrors(errorPayload);
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);
        return this.handleExceptionInternal(ex, apiResponse, headers, status, exchange);

    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        ex.printStackTrace();
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccess(false);
        apiResponse.setCode(400);

        ErrorPayload payload = new ErrorPayload();
        List<org.example.orgflow.exceptions.model.FieldError> errors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            String field = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            errors.add(org.example.orgflow.exceptions.model.FieldError.builder()
                    .filedName(field)
                    .nameAr(ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(errorMessage, null, new Locale("ar"))) ? messageSource.getMessage(errorMessage, null, new Locale("ar")) : errorMessage)
                    .nameEn(ObjectChecker.isNotEmptyOrNull(messageSource.getMessage(errorMessage, null, new Locale("en"))) ? messageSource.getMessage(errorMessage, null, new Locale("en")) : errorMessage)
                    .build());
        });
        payload.setFieldErrors(errors);
        apiResponse.setPayload(payload);

        return new ResponseEntity<Object>(apiResponse, HttpStatus.BAD_REQUEST);


    }


}
