package com.gft.taskoverflow.task;

public record TaskShortDto(Long id, String title, Long taskListId, Priority priority, boolean done) {
}
