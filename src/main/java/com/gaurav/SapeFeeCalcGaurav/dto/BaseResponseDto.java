package com.gaurav.SapeFeeCalcGaurav.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.UUID;

/**
 * Base response
 *
 * @param <T>
 */
@Getter
@Setter
public class BaseResponseDto<T> {

    protected T data;

    protected String httpStatus;

    protected String id = UUID.randomUUID().toString();

    protected Date createdAt = new Date();

    public BaseResponseDto(T data, HttpStatus status) {
        this.data = data;
        this.httpStatus = status.value() + "";
    }

    public static <T> BaseResponseDto<T> of(T data) {
        return new BaseResponseDto<T>(data, HttpStatus.OK);
    }


}
