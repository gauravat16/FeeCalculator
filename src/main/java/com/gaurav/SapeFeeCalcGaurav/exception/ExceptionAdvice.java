package com.gaurav.SapeFeeCalcGaurav.exception;

import com.gaurav.SapeFeeCalcGaurav.dto.BaseResponseDto;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handles all errors here.
 */
@RestControllerAdvice
public class ExceptionAdvice {

    private Logger logger = LoggerFactory.getLogger(ExceptionAdvice.class);

    @ExceptionHandler(value = {BaseFeeApplicationException.class})
    private ResponseEntity<BaseResponseDto<String>> handleBaseException(BaseFeeApplicationException e) {
        logger.debug("Exception occurred - ", e);
        BaseResponseDto baseResponseDto = new BaseResponseDto<>(e.getMessage(), e.getStatus());
        return new ResponseEntity<>(baseResponseDto, e.getStatus());
    }

    @ExceptionHandler(value = {BaseFeeApplicationRuntimeException.class})
    private ResponseEntity<BaseResponseDto<String>> handleBaseException(BaseFeeApplicationRuntimeException e) {
        logger.debug("Exception occurred - ", e);
        BaseResponseDto baseResponseDto = new BaseResponseDto<>(e.getMessage(), e.getStatus());
        return new ResponseEntity<>(baseResponseDto, e.getStatus());
    }

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    private ResponseEntity<BaseResponseDto<String>> handleBaseException(IllegalArgumentException e) {
        logger.debug("Exception occurred - ", e);
        BaseResponseDto baseResponseDto = new BaseResponseDto<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(baseResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
