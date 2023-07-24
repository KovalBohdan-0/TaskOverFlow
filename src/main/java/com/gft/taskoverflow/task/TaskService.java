package com.gft.taskoverflow.task;

import com.gft.taskoverflow.customer.CustomerService;
import com.gft.taskoverflow.exception.TaskNotFoundException;
import com.gft.taskoverflow.task.dto.TaskCreationDto;
import com.gft.taskoverflow.task.dto.TaskMoveDto;
import com.gft.taskoverflow.task.dto.TaskPreviewDto;
import com.gft.taskoverflow.task.list.TaskListService;
import lombok.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class TaskService {
    private final TaskRepository taskRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final TaskMapper taskMapper;
    private final TaskListService taskListService;
    private final CustomerService customerService;

    public List<TaskPreviewDto> getTasksByTaskListId(Long taskListId) {
        return taskRepository.findAllByTaskListId(taskListId).stream().map(taskMapper::mapToShortDto).toList();
    }

    public TaskPreviewDto addTask(TaskCreationDto task, Long taskListId, Long boardId) {
        Task newTask = taskMapper.mapToTask(task);
        newTask.setTaskList(taskListService.getTaskListById(taskListId));
        taskRepository.save(newTask);
        return taskMapper.mapToShortDto(newTask);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public void moveTaskToAnotherList(TaskMoveDto taskMoveDto) {
        Task task = getTaskById(taskMoveDto.id());
        task.setTaskList(taskListService.getTaskListById(taskMoveDto.taskListId()));
        taskRepository.save(task);
    }

    private Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    }
}
