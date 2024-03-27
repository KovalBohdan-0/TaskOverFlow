package com.tof.user.exception;

import com.tof.user.exception.exceptions.LoginException;
import com.tof.user.exception.exceptions.DuplicatedResourceException;
import com.tof.user.exception.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(DuplicatedResourceException.class)
    private ResponseEntity<Object> handleException(DuplicatedResourceException exception) {
        return buildExceptionBody(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LoginException.class)
    private ResponseEntity<Object> handleException(LoginException exception) {
        return buildExceptionBody(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<Object> handleException(NotFoundException exception) {
        return buildExceptionBody(exception, HttpStatus.NOT_FOUND);
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
