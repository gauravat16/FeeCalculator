package com.gaurav.SapeFeeCalcGaurav.exception;

import org.springframework.http.HttpStatus;

/**
 * Base Run time exception.
 */
public abstract class BaseFeeApplicationRuntimeException extends RuntimeException {

    public BaseFeeApplicationRuntimeException(String message) {
        super(message);
    }

    public BaseFeeApplicationRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    abstract HttpStatus getStatus();
}
