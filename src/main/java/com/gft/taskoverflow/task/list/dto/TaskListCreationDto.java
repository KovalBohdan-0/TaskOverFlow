package com.gft.taskoverflow.task.list.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TaskListCreationDto (@NotEmpty @Size(max = 100) String title, @NotNull Long boardId) {
}
