package com.gft.taskoverflow.task;

import com.gft.taskoverflow.task.dto.TaskCreationDto;
import com.gft.taskoverflow.task.dto.TaskDeleteDto;
import com.gft.taskoverflow.task.dto.TaskDto;
import com.gft.taskoverflow.task.dto.TaskPreviewDto;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Data
public class TaskSocketController {
    private final TaskService taskService;

    @MessageMapping("/task-add/{boardId}")
    @SendTo("/topic/task-added/{boardId}")
    public TaskPreviewDto addTask(@Valid @Payload TaskCreationDto task) {
        return taskService.addTask(task, task.taskListId());
    }

    @MessageMapping("/task-delete/{boardId}")
    @SendTo("/topic/task-deleted/{boardId}")
    public TaskDeleteDto deleteTask(@Valid @Payload Long taskId) {
        return taskService.deleteTask(taskId);
    }

    @MessageMapping("/task-update/{boardId}")
    @SendTo("/topic/task-updated/{boardId}")
    public TaskDto updateTask(@Valid @Payload TaskDto taskDto) {
        return taskService.updateTask(taskDto);
    }

    @MessageMapping("/task-move/{boardId}")
    @SendTo("/topic/task-moved/{boardId}")
    public TaskDto moveTask(@Valid @Payload TaskDto taskDto) {
        return taskService.moveTask(taskDto);
    }
}
