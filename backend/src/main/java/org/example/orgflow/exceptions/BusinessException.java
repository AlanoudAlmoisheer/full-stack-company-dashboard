package org.example.orgflow.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public abstract class BusinessException extends Exception {

    private String message;

    private HttpStatus status;


}
