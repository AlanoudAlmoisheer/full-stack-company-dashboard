package org.example.orgflow.exceptions;

import org.springframework.http.HttpStatus;

public class RecordNotFoundException extends BusinessException {

    public RecordNotFoundException() {
        super("exception.record.not_found", HttpStatus.NOT_FOUND);
    }
}
