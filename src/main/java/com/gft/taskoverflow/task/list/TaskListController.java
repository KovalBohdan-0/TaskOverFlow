package com.gft.taskoverflow.task.list;

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

    @PostMapping("/board/{boardId}")
    public void addTaskList(@RequestBody TaskList taskList, @PathVariable Long boardId) {
        taskListService.addTaskList(taskList, boardId);
    }

    @DeleteMapping("/{taskListId}")
    public void deleteTaskList(@PathVariable Long taskListId) {
        taskListService.deleteTaskList(taskListId);
    }

    @PutMapping("/{taskListId}")
    public void renameTaskList(@PathVariable Long taskListId, @RequestBody String title) {
        taskListService.renameTaskList(taskListId, title);
    }
}
