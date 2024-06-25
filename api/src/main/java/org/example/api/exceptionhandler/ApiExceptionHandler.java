package org.example.api.exceptionhandler;

import lombok.extern.slf4j.Slf4j;
import org.example.api.common.api.Api;
import org.example.api.common.error.ErrorCodeIfs;
import org.example.api.common.exception.ApiException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@Order(Integer.MIN_VALUE)
public class ApiExceptionHandler {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<Api<Object>> apiException(ApiException apiException){

        log.error("",apiException);

        ErrorCodeIfs errorCode = apiException.getErrorCodeIfs();

        return ResponseEntity
                .status(errorCode.getHttpStatusCode())
                .body(
                        Api.Error(errorCode, apiException.getErrorDescription())
                );
    }
}
