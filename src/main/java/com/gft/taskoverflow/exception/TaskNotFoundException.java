package com.gft.taskoverflow.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends RuntimeException {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskListNotFoundException.class);

    public TaskNotFoundException(Long id){
        LOGGER.error("Task with {} id not found", id);
    }
}
