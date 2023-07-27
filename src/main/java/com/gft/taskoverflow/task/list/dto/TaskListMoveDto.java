package com.gft.taskoverflow.task.list.dto;

import jakarta.validation.constraints.NotNull;

public record TaskListMoveDto (@NotNull Long taskListBeforeId, @NotNull Long taskListAfterId, @NotNull Long taskListId) {
}
