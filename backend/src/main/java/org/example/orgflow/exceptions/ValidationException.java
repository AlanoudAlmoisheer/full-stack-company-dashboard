package org.example.orgflow.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationException extends BusinessException {
    public ValidationException() {
        super("error.notfound", HttpStatus.NOT_FOUND);
    }

}
