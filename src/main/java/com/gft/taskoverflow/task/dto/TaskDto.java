package com.gft.taskoverflow.task.dto;

import com.gft.taskoverflow.task.Priority;

import java.time.LocalDateTime;

public record TaskDto(Long id, String title, String description, boolean done, Priority priority,
                      LocalDateTime createdAt, LocalDateTime deadline, Long taskListId, Float position) {
}
