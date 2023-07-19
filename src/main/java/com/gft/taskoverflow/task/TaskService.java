package com.gft.taskoverflow.task;

import com.gft.taskoverflow.exception.TaskNotFoundException;
import com.gft.taskoverflow.task.list.TaskListService;
import lombok.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Data
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskListService taskListService;
    private final SimpMessagingTemplate messagingTemplate;

    public void addTask(TaskDto taskDto) {
        Task task = new Task();
        task.setTitle(taskDto.title());
        task.setDescription(taskDto.description());
        task.setTaskList(taskListService.getTaskListById(taskDto.taskListId()));
        task.setPriority(taskDto.priority());
        task.setDeadline(taskDto.deadline());
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
