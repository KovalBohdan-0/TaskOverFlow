package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.task.list.dto.TaskListResponseDto;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("/api/v1/task-lists")
public class TaskListController {
    private final TaskListService taskListService;

    @GetMapping("/{taskListId}")
    public TaskList getTaskListById(@PathVariable Long taskListId) {
        return taskListService.getTaskListById(taskListId);
    }

    @GetMapping("/board/{boardId}")
    public List<TaskListResponseDto> getBoardTaskLists(@PathVariable Long boardId) {
        return taskListService.getBoardTaskListsResponse(boardId);
    }
}
