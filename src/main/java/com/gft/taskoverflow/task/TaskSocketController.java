package com.gft.taskoverflow.task;

import com.gft.taskoverflow.task.dto.TaskCreationDto;
import com.gft.taskoverflow.task.dto.TaskPreviewDto;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.messaging.handler.annotation.DestinationVariable;
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
    public TaskPreviewDto addTask(@DestinationVariable Long boardId, @Valid @Payload TaskCreationDto task) {
        return taskService.addTask(task, task.taskListId(), boardId);
    }
}
