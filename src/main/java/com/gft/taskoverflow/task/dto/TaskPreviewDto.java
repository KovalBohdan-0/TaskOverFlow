package com.gft.taskoverflow.task.dto;

import com.gft.taskoverflow.task.Priority;

public record TaskPreviewDto(Long id, String title, Long taskListId, Priority priority, boolean done) {
}
