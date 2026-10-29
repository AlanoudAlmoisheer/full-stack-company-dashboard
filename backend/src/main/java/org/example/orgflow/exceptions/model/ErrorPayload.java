package org.example.orgflow.exceptions.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorPayload implements Serializable {

    private String messageEn;

    private String messageAr;
    private String errorCode;
    private String type;
    @Builder.Default
    private LocalDateTime timestamp = LocalDateTime.now();

    private String detailsAsString;

    private List<Error> errors;

    private List<FieldError> fieldErrors;


    public ErrorPayload(String messageEn, List<Error> errors) {
        super();
        this.messageEn = messageEn;
        this.errors = errors;
    }

    public ErrorPayload(String messageEn, String messageAr, List<Error> errors) {
        super();
        this.messageEn = messageEn;
        this.messageAr = messageAr;
        this.errors = errors;
    }


    public ErrorPayload(String messageEn, String errorEn) {
        super();
        this.messageEn = messageEn;
        errors = Arrays.asList(new Error(errorEn));
    }
}
