package com.gft.taskoverflow.task;

import com.gft.taskoverflow.task.dto.TaskDto;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;

    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskDtoById(id);
    }
}
