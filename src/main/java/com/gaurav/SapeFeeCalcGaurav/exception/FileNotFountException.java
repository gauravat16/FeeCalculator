package com.gaurav.SapeFeeCalcGaurav.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception occurred during reading file runtime time exception.
 */
public class FileNotFountException extends BaseFeeApplicationRuntimeException {

    public FileNotFountException(String message) {
        super(message);
    }

    public FileNotFountException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    HttpStatus getStatus() {
        return HttpStatus.NOT_FOUND;
    }
}
