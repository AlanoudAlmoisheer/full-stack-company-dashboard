package org.example.orgflow.exceptions;

import org.springframework.http.HttpStatus;

public class RecordDuplicateException extends BusinessException {

    public RecordDuplicateException() {
        super("error.notfound", HttpStatus.NOT_FOUND);
    }


}
