package com.gft.taskoverflow.task;

import com.gft.taskoverflow.task.dto.TaskCreationDto;
import com.gft.taskoverflow.task.dto.TaskDeleteDto;
import com.gft.taskoverflow.task.dto.TaskDto;
import com.gft.taskoverflow.task.dto.TaskPreviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "taskList.id", target = "taskListId")
    TaskPreviewDto mapToPreviewDto(Task task);
    Task mapToTask(TaskCreationDto taskCreationDto);
    @Mapping(target = "taskListId", source = "taskList.id")
    TaskDto mapToDto(Task task);
    @Mapping(target = "taskListId", source = "taskList.id")
    TaskDeleteDto mapToDeleteDto(Task task);
}
