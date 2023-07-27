package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.task.list.dto.TaskListCreationDto;
import com.gft.taskoverflow.task.list.dto.TaskListMoveDto;
import com.gft.taskoverflow.task.list.dto.TaskListRenameDto;
import com.gft.taskoverflow.task.list.dto.TaskListResponseDto;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@Data
public class TaskListSocketController {
    private final TaskListService taskListService;

    @MessageMapping("/task-list-add/{boardId}")
    @SendTo("/topic/task-list-added/{boardId}")
    public TaskListResponseDto addTaskList(@Valid @Payload TaskListCreationDto taskList) {
        return taskListService.addTaskList(taskList);
    }

    @MessageMapping("/task-list-delete/{boardId}")
    @SendTo("/topic/task-list-deleted/{boardId}")
    public Long deleteTaskList(@Valid @Payload Long taskListId) {
        return taskListService.deleteTaskList(taskListId);
    }

    @MessageMapping("/task-list-rename/{boardId}")
    @SendTo("/topic/task-list-renamed/{boardId}")
    public TaskListRenameDto renameTaskList(@Valid @Payload TaskListRenameDto taskList) {
        return taskListService.renameTaskList(taskList);
    }

    @MessageMapping("/task-list-move/{boardId}")
    @SendTo("/topic/task-list-moved/{boardId}")
    public TaskListResponseDto moveTaskList(@Valid @Payload TaskListMoveDto taskListMoveDto) {
        return taskListService.moveTaskList(taskListMoveDto);
    }
}
