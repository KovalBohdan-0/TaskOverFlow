package com.gft.taskoverflow.task;

import com.gft.taskoverflow.customer.CustomerService;
import com.gft.taskoverflow.exception.TaskNotFoundException;
import com.gft.taskoverflow.task.dto.TaskCreationDto;
import com.gft.taskoverflow.task.dto.TaskDeleteDto;
import com.gft.taskoverflow.task.dto.TaskDto;
import com.gft.taskoverflow.task.dto.TaskPreviewDto;
import com.gft.taskoverflow.task.list.TaskListService;
import lombok.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;


@Service
@Data
public class TaskService {
    private final TaskRepository taskRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final TaskMapper taskMapper;
    private final TaskListService taskListService;
    private final CustomerService customerService;

    public TaskDto getTaskDtoById(Long taskId) {
        return taskMapper.mapToDto(taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId)));
    }

    public TaskPreviewDto addTask(TaskCreationDto task, Long taskListId, Long boardId) {
        Task newTask = taskMapper.mapToTask(task);
        newTask.setTaskList(taskListService.getTaskListById(taskListId));
        taskRepository.save(newTask);
        return taskMapper.mapToShortDto(newTask);
    }

    public TaskDto updateTask(TaskDto taskDto) {
        Task task = getTaskById(taskDto.id());
        task.setTaskList(taskListService.getTaskListById(taskDto.taskListId()));
        task.setDescription(taskDto.description());
        task.setPriority(taskDto.priority());
        task.setDone(taskDto.done());
        task.setDeadline(taskDto.deadline());
        task.setTitle(taskDto.title());
        return taskMapper.mapToDto(taskRepository.save(task));
    }


    public TaskDeleteDto deleteTask(Long taskId) {
        TaskDeleteDto taskDeleteDto = taskMapper.mapToDeleteDto(getTaskById(taskId));
        taskRepository.deleteById(taskId);
        return taskDeleteDto;
    }

    private Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    }
}
