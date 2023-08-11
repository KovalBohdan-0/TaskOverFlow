package com.gft.taskoverflow.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT)
public class EmailAlreadyConfirmedException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailAlreadyConfirmedException.class);

    public EmailAlreadyConfirmedException() {
        super("Token for this email already confirmed");
        LOGGER.error("Token for this email: already confirmed");
    }
}
