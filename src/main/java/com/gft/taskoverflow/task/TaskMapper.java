package com.gft.taskoverflow.task;

import com.gft.taskoverflow.exception.TaskListNotFoundException;
import com.gft.taskoverflow.task.list.TaskListRepository;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class TaskMapper {
    private final TaskListRepository taskListRepository;

    public TaskShortDto toShortDto(Task task) {
        return new TaskShortDto(task.getId(), task.getTitle(), task.getTaskList().getId(), task.getPriority(), task.isDone());
    }


    public Task toEntity(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setPriority(taskDto.priority());
        task.setDeadline(taskDto.deadline());
        task.setTaskList(taskListRepository.findById(taskDto.taskListId()).orElseThrow(() -> new TaskListNotFoundException(taskDto.taskListId())));

        return task;
    }

}
