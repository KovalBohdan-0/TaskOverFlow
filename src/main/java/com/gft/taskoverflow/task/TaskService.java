package com.gft.taskoverflow.task;

import com.gft.taskoverflow.exception.TaskNotFoundException;
import com.gft.taskoverflow.task.list.TaskListService;
import lombok.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
public class TaskService {
    private final TaskRepository taskRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final TaskMapper taskMapper;
    private final TaskListService taskListService;

    public List<TaskShortDto> getTasksByTaskListId(Long taskListId) {
        return taskRepository.findAllByTaskListId(taskListId).stream().map(taskMapper::toShortDto).toList();
    }

    public void addTask(TaskDto taskDto) {
        Task task = taskMapper.toEntity(taskDto);
        taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }

    public void markTaskAsDone(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setDone(true);
        taskRepository.save(task);
    }

    public void renameTask(Long taskId, String title) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setTitle(title);
        taskRepository.save(task);
    }

    public void changeTaskDescription(Long taskId, String description) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setDescription(description);
        taskRepository.save(task);
    }

    public void changeTaskPriority(Long taskId, Priority priority) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setPriority(priority);
        taskRepository.save(task);
    }

    public void changeTaskDeadline(Long taskId, LocalDateTime deadline) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setDeadline(deadline);
        taskRepository.save(task);
    }

    public void moveTaskToAnotherList(Long taskId, Long taskListId) {
        Task task = taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
        task.setTaskList(taskListService.getTaskListById(taskListId));
        taskRepository.save(task);
    }
}
