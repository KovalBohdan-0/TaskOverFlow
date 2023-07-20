package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.task.TaskShortDto;

import java.util.List;

public record TaskListResponseDto(Long id, String title, Long boardId, List<TaskShortDto> tasks) {
}
