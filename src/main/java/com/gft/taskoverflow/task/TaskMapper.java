package com.gft.taskoverflow.task;

import com.gft.taskoverflow.task.dto.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "taskList.id", target = "taskListId")
    TaskPreviewDto mapToPreviewDto(Task task);
    @Mapping(target = "taskList", ignore = true)
    @Mapping(target = "position", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "done", ignore = true)
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "deadline", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Task mapToTask(TaskCreationDto taskCreationDto);
    @Mapping(target = "taskListId", source = "taskList.id")
    TaskDto mapToDto(Task task);
    @Mapping(target = "taskListId", source = "taskList.id")
    TaskDeleteDto mapToDeleteDto(Task task);
    @Mapping(target = "taskListId", source = "task.taskList.id")
    TaskMovedDto mapToMovedDto(Task task, Long previousTaskListId);
}
