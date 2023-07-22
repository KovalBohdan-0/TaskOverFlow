package com.gft.taskoverflow.task.dto;

import com.gft.taskoverflow.task.Priority;

public record TaskCreationDto (String title, Priority priority, Long taskListId) {
}
