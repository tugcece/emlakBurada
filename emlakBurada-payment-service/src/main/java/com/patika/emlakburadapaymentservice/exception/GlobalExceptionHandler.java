package com.patika.emlakburadapaymentservice.exception;

import com.patika.emlakburadapaymentservice.dto.response.ExceptionResponse;
import com.patika.emlakburadapaymentservice.dto.response.GenericResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EmlakBuradaException.class)
    public GenericResponse<ExceptionResponse> handleException(EmlakBuradaException exception) {
        log.error(exception.getLocalizedMessage());
        return GenericResponse.failed(exception.getMessage());
    }

}
