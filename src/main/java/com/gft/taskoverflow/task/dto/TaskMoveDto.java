package com.gft.taskoverflow.task.dto;

import jakarta.validation.constraints.NotNull;

public record TaskMoveDto (@NotNull Long taskBeforeId, @NotNull Long taskAfterId, @NotNull Long taskId, @NotNull Long taskListId) {
}
