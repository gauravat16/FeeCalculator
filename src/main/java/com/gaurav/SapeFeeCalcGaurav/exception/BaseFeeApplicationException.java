package com.gaurav.SapeFeeCalcGaurav.exception;

import org.springframework.http.HttpStatus;

/**
 * Base Compile time exception.
 */
public abstract class BaseFeeApplicationException extends Exception {
    public BaseFeeApplicationException(String message) {
        super(message);
    }

    public BaseFeeApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

    abstract HttpStatus getStatus();
}
