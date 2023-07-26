package com.gft.taskoverflow.task;

import com.gft.taskoverflow.customer.CustomerService;
import com.gft.taskoverflow.exception.TaskNotFoundException;
import com.gft.taskoverflow.task.dto.*;
import com.gft.taskoverflow.task.list.TaskListService;
import lombok.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;


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

    public TaskPreviewDto addTask(TaskCreationDto task, Long taskListId) {
        Task newTask = taskMapper.mapToTask(task);
        newTask.setTaskList(taskListService.getTaskListById(taskListId));
        newTask.setPosition(taskRepository.findMaxPositionByTaskListId(taskListId).orElse(0.0f) + 1);
        taskRepository.save(newTask);
        return taskMapper.mapToPreviewDto(newTask);
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

    public TaskDto moveTask(TaskMoveDto taskMoveDto) {
        Task task = getTaskById(taskMoveDto.taskId());
        Optional<Task> taskBefore = taskRepository.findById(taskMoveDto.taskBeforeId());
        Optional<Task> taskAfter = taskRepository.findById(taskMoveDto.taskAfterId());

        if (taskBefore.isPresent() && taskAfter.isPresent()) {
            task.setPosition((taskBefore.get().getPosition() + taskAfter.get().getPosition()) / 2);
        } else if (taskBefore.isPresent()) {
            task.setPosition(taskBefore.get().getPosition() + 1);
        } else taskAfter.ifPresent(value -> task.setPosition(value.getPosition() - 1));

        task.setTaskList(taskListService.getTaskListById(taskMoveDto.taskListId()));
        taskRepository.save(task);
        return taskMapper.mapToDto(task);
    }

    private Task getTaskById(Long taskId) {
        return taskRepository.findById(taskId).orElseThrow(() -> new TaskNotFoundException(taskId));
    }
}
