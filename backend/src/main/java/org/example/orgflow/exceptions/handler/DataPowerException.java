package org.example.orgflow.exceptions.handler;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataPowerException extends RuntimeException {

    private String message;
    //HTTP response status
    private HttpStatus status;

    public DataPowerException() {
    }

    public DataPowerException(String message, HttpStatus status) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
