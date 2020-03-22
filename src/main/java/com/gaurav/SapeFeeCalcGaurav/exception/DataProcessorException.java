package com.gaurav.SapeFeeCalcGaurav.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception occurred during data processing runtime time exception.
 */
public class DataProcessorException extends BaseFeeApplicationRuntimeException {
    public DataProcessorException(String message) {
        super(message);
    }

    public DataProcessorException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    HttpStatus getStatus() {
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
