package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.task.list.dto.TaskListCreationDto;
import com.gft.taskoverflow.task.list.dto.TaskListResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskListMapper {
    @Mapping(source = "board.id", target = "boardId")
    TaskListResponseDto mapToResponseDto(TaskList taskList);
    @Mapping(target = "sortOption", ignore = true)
    @Mapping(target = "sortDirection", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "tasks", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "board", ignore = true)
    @Mapping(target = "assignedCustomers", ignore = true)
    TaskList mapToTaskList(TaskListCreationDto taskListCreationDto);
}
