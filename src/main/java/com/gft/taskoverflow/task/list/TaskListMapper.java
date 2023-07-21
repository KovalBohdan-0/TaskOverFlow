package com.gft.taskoverflow.task.list;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskListMapper {
    @Mapping(source = "board.id", target = "boardId")
    TaskListResponseDto mapToResponseDto(TaskList taskList);
}
