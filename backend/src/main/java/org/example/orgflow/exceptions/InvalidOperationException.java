package org.example.orgflow.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidOperationException extends BusinessException {
    public InvalidOperationException() {
        super("error.notfound", HttpStatus.BAD_REQUEST);
    }

}
