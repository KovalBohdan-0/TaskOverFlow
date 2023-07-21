package com.gft.taskoverflow.task;

import com.gft.taskoverflow.task.dto.TaskCreationDto;
import com.gft.taskoverflow.task.dto.TaskPreviewDto;
import lombok.Data;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Data
public class TaskSocketController {
    private final TaskService taskService;

    @MessageMapping("/task-added")
    @SendTo("/topic/task-added")
    public TaskPreviewDto task(@Payload TaskCreationDto task) throws Exception {
        return taskService.addTask(task, task.taskListId());
    }
}
