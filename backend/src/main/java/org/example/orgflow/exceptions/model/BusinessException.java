package org.example.orgflow.exceptions.model;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
public class BusinessException extends RuntimeException {

    private HttpStatus status;
    private String message;

    private List<String> errorKeys;


    public BusinessException() {
        super();
    }


    public BusinessException(final String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(final String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public BusinessException(String message, List<String> errorKeys) {
        super();
        this.message = message;
        this.errorKeys = errorKeys;
    }

    public BusinessException(final Throwable cause) {
        super(cause);
    }

}
