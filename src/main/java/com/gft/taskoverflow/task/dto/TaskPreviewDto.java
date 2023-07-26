package com.gft.taskoverflow.task.dto;

import com.gft.taskoverflow.task.Priority;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record TaskPreviewDto(@NotNull Long id, @NotEmpty @Size(max = 100) String title, @NotNull Float position, @NotNull Long taskListId, @NotNull Priority priority, boolean done) {
}
