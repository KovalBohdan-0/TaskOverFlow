package com.gft.taskoverflow.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class IncorrectPasswordException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(IncorrectPasswordException.class);

    public IncorrectPasswordException(String message) {
        super(message);
        LOGGER.error(message);
    }
}
