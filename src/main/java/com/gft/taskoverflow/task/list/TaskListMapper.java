package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.board.BoardService;
import com.gft.taskoverflow.task.Task;
import com.gft.taskoverflow.task.TaskRepository;
import com.gft.taskoverflow.task.TaskService;
import com.gft.taskoverflow.task.TaskShortDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Data
public class TaskListMapper {
    private final BoardService boardService;
    private final TaskRepository taskRepository;

    public TaskListResponseDto toResponseDto(TaskList taskList) {
        List<TaskShortDto> tasks = taskRepository.findAllByTaskListId(taskList.getId()).stream().map(this::toShortDto).toList();
        return new TaskListResponseDto(taskList.getId(), taskList.getTitle(), taskList.getBoard().getId(), tasks);
    }

    public TaskShortDto toShortDto(Task task) {
        return new TaskShortDto(task.getId(), task.getTitle(), task.getTaskList().getId(), task.getPriority(), task.isDone());
    }

    public TaskList toEntity(TaskListDto taskListDto) {
        TaskList taskList = new TaskList();
        taskList.setTitle(taskListDto.title());
        taskList.setBoard(boardService.getBoardById(taskListDto.boardId()));

        return taskList;
    }
}
