package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.task.list.dto.TaskListCreationDto;
import com.gft.taskoverflow.task.list.dto.TaskListRenameDto;
import com.gft.taskoverflow.task.list.dto.TaskListResponseDto;
import lombok.Data;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Data
public class TaskListSocketController {
    private final TaskListService taskListService;

    @MessageMapping("/task-list-add")
    @SendTo("/topic/task-list-added")
    public TaskListResponseDto addTaskList(@Payload TaskListCreationDto taskList) {
        return taskListService.addTaskList(taskList);
    }

    @MessageMapping("/task-list-delete")
    @SendTo("/topic/task-list-deleted")
    public Long deleteTaskList(@Payload Long taskListId) {
        return taskListService.deleteTaskList(taskListId);
    }

    @MessageMapping("/task-list-rename")
    @SendTo("/topic/task-list-renamed")
    public TaskListRenameDto renameTaskList(@Payload TaskListRenameDto taskList) {
        return taskListService.renameTaskList(taskList);
    }
}
