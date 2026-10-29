package org.example.orgflow.exceptions.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Data
@Setter
@Getter
public class ApiError implements Serializable {

    private HttpStatus status;
    private String messageEn;

    private String messageAr;
    private List<Error> errors;
    private List<FieldError> fieldErrors;

    public ApiError(HttpStatus status, List<FieldError> fieldErrors) {
        super();
        this.status = status;
        this.fieldErrors = fieldErrors;
    }

    public ApiError(HttpStatus status, String messageEn, String messageAr, List<Error> errors) {
        super();
        this.status = status;
        this.messageEn = messageEn;
        this.messageAr = messageAr;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String messageEn, List<Error> errors) {
        super();
        this.status = status;
        this.messageEn = messageEn;
        this.errors = errors;
    }

    public ApiError(HttpStatus status, String messageEn, String messageAr, String errorEn, String errorAr) {
        super();
        this.status = status;
        this.messageEn = messageEn;
        this.messageAr = messageAr;
        errors = Arrays.asList(new Error(errorEn, errorAr));
    }

    public ApiError(HttpStatus status, String messageEn, String errorEn) {
        super();
        this.status = status;
        this.messageEn = messageEn;
        errors = Arrays.asList(new Error(errorEn));
    }

}
