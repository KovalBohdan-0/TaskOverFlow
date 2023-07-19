package com.gft.taskoverflow.task.list;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/v1/task-lists")
public class TaskListSocketController {
    private final TaskListService taskListService;

    @PostMapping
    public void addTaskList(@RequestBody TaskListDto taskListDto) {
        taskListService.addTaskList(taskListDto);
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
