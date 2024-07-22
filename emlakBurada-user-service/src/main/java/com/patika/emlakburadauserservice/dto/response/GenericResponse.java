package com.patika.emlakburadauserservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class GenericResponse<T> {

    private String message;
    private LocalDateTime date;
    private HttpStatus httpStatus;
    private T data;

    public static GenericResponse<ExceptionResponse> failed(String message) {
        return GenericResponse.<ExceptionResponse>builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message(message)
                .data(new ExceptionResponse(message))
                .date(LocalDateTime.now())
                .build();
    }

    public static <T> GenericResponse<T> success(T data) {
        return GenericResponse.<T>builder()
                .message("SUCCESS")
                .date(LocalDateTime.now())
                .httpStatus(HttpStatus.OK)
                .data(data)
                .build();
    }

}
