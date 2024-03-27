package com.tof.gateway.Gateway.exception;

import com.tof.gateway.Gateway.exception.exceptions.LoginException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    private ResponseEntity<Object> handleException(UsernameNotFoundException exception) {
        return buildExceptionBody(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginException.class)
    private ResponseEntity<Object> handleException(LoginException exception) {
        return buildExceptionBody(exception, HttpStatus.UNAUTHORIZED);
    }

    private ResponseEntity<Object> buildExceptionBody(Exception exception, HttpStatus httpStatus) {
        ErrorResponse exceptionResponse = ErrorResponse.builder()
                .status(httpStatus.value())
                .message(exception.getMessage())
                .timestamp(System.currentTimeMillis())
                .build();
        log.debug(exception.getMessage());
        return ResponseEntity.status(httpStatus).body(exceptionResponse);
    }
}
