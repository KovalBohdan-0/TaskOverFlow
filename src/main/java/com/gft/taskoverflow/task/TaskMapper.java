package com.gft.taskoverflow.task;

import com.gft.taskoverflow.task.dto.TaskCreationDto;
import com.gft.taskoverflow.task.dto.TaskPreviewDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @Mapping(source = "taskList.id", target = "taskListId")
    TaskPreviewDto mapToShortDto(Task task);
    Task mapToTask(TaskCreationDto taskCreationDto);
}
