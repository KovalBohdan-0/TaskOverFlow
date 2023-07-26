package com.gft.taskoverflow.task.dto;

import com.gft.taskoverflow.task.Priority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskCreationDto (@NotEmpty @Size(max = 100) String title, @NotNull Priority priority, @NotNull Long taskListId) {
}
