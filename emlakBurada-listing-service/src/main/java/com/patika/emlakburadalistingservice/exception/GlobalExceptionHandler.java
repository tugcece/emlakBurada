package com.patika.emlakburadalistingservice.exception;

import com.patika.emlakburadalistingservice.dto.response.ExceptionResponse;
import com.patika.emlakburadalistingservice.dto.response.GenericResponse;
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
