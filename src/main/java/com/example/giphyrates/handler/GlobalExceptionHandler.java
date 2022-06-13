package com.example.giphyrates.handler;

import com.example.giphyrates.model.ErrorResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponseBody handleException(final IllegalArgumentException e) {
        String message = Optional.ofNullable(e.getMessage())
                .orElse("Illegal argument");
        return new ErrorResponseBody(message, LocalDateTime.now());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponseBody handleException(final RuntimeException e) {
        String message = Optional.ofNullable(e.getMessage())
                .orElse("Internal Server Error");
        return new ErrorResponseBody(message, LocalDateTime.now());
    }
}
