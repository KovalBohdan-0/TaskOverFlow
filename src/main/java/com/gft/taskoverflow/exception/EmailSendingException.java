package com.gft.taskoverflow.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class EmailSendingException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailSendingException.class);

    public EmailSendingException(String email) {
        super("Email to: %s was not sent".formatted(email));
        LOGGER.error("Email to: {} was not sent", email);
    }
}
