package com.gft.taskoverflow.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class UploadLimitExceededException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(UploadLimitExceededException.class);

    public UploadLimitExceededException(Long id){
        super("Upload limit exceeded for user with " + id + " id");
        LOGGER.error("Upload limit exceeded for user with {} id", id);
    }
}
