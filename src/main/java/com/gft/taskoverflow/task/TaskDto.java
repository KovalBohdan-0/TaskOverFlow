package com.gft.taskoverflow.task;

import java.time.LocalDateTime;

public record TaskDto(String title, String description, Long taskListId, Priority priority, LocalDateTime deadline) {
}
