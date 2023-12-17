package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.task.list.dto.TaskListResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@SecurityRequirement(name = "Bearer Authentication")
@RequestMapping("/api/v1/task-lists")
public class TaskListController {
    private final TaskListService taskListService;

    @Operation(summary = "Get task list by id")
    @ApiResponse(responseCode = "200", description = "Task list")
    @ApiResponse(responseCode = "404", description = "Task list not found", content = @Content)
    @GetMapping("/{taskListId}")
    public TaskList getTaskListById(@PathVariable Long taskListId) {
        return taskListService.getTaskListById(taskListId);
    }

    @Operation(summary = "Get all task lists")
    @ApiResponse(responseCode = "200", description = "List of task lists")
    @GetMapping("/board/{boardId}")
    public List<TaskListResponseDto> getBoardTaskLists(@PathVariable Long boardId) {
        return taskListService.getBoardTaskListsResponse(boardId);
    }
}
