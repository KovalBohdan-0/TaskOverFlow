package com.gft.taskoverflow.task.dto;

public record TaskMoveDto (Long taskBeforeId, Long taskAfterId, Long taskId, Long taskListId) {
}
