package com.gft.taskoverflow.task;

import com.gft.taskoverflow.task.dto.TaskDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/task")
public class TaskController {
    private final TaskService taskService;

    @Operation(summary = "Get task by id")
    @ApiResponse(responseCode = "200", description = "Task")
    @ApiResponse(responseCode = "404", description = "Task not found", content = @Content)
    @GetMapping("/{id}")
    public TaskDto getTaskById(@PathVariable Long id) {
        return taskService.getTaskDtoById(id);
    }
}
