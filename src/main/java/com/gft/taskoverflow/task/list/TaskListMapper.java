package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.task.list.dto.TaskListCreationDto;
import com.gft.taskoverflow.task.list.dto.TaskListResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskListMapper {
    @Mapping(source = "board.id", target = "boardId")
    TaskListResponseDto mapToResponseDto(TaskList taskList);
    TaskList mapToTaskList(TaskListCreationDto taskListCreationDto);
}
