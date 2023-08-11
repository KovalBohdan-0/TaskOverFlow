package com.gft.taskoverflow.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.GONE)
public class TokenExpiredException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(TokenExpiredException.class);

    public TokenExpiredException(String token) {
        super("This token already expired");
        LOGGER.error("This token already expired {}", token);
    }
}
