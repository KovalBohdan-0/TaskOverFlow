package com.gft.taskoverflow.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class TaskListNotFoundException extends RuntimeException{

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskListNotFoundException.class);

    public TaskListNotFoundException(Long id){
        LOGGER.error("TaskList with {} id not found", id);
    }
}