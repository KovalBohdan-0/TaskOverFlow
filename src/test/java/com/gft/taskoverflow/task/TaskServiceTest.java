package com.gft.taskoverflow.task;

import com.gft.taskoverflow.task.dto.*;
import com.gft.taskoverflow.task.list.TaskList;
import com.gft.taskoverflow.task.list.TaskListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskListService taskListService;

    @Mock
    private TaskMapper taskMapper;

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTaskDtoById() {
        Long taskId = 1L;
        Task task = new Task();
        TaskDto taskDto = new TaskDto(3L, "title",
                "description", false, Priority.LOW, LocalDateTime.now(), LocalDateTime.now(),
                4L, 1.0f);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.mapToDto(task)).thenReturn(taskDto);

        taskService.getTaskDtoById(taskId);

        verify(taskRepository).findById(taskId);
    }

    @Test
    public void testAddTask() {
        TaskCreationDto taskCreationDto = new TaskCreationDto("title", Priority.LOW, 1L);
        Long taskListId = 1L;
        Task newTask = new Task();
        TaskPreviewDto taskPreviewDto = new TaskPreviewDto(1L, "title", 1.0f, 2L, Priority.LOW, false, LocalDateTime.now(), LocalDateTime.now());
        when(taskMapper.mapToTask(taskCreationDto)).thenReturn(newTask);
        when(taskListService.getTaskListById(taskListId)).thenReturn(new TaskList());
        when(taskMapper.mapToPreviewDto(newTask)).thenReturn(taskPreviewDto);
        when(taskRepository.save(any())).thenReturn(newTask);
        when(taskRepository.findMaxPositionByTaskListId(taskListId)).thenReturn(Optional.of(0.0f));

        taskService.addTask(taskCreationDto, taskListId);

        verify(taskRepository).save(any());
    }

    @Test
    public void testUpdateTask() {
        TaskDto taskDto = new TaskDto(3L, "title",
                "description", false, Priority.LOW, LocalDateTime.now(), LocalDateTime.now(),
                4L, 1.0f);
        Task task = new Task();
        when(taskRepository.save(any())).thenReturn(task);
        when(taskRepository.findById(taskDto.id())).thenReturn(Optional.of(task));
        when(taskListService.getTaskListById(taskDto.taskListId())).thenReturn(new TaskList());

        taskService.updateTask(taskDto);

        verify(taskRepository).save(any());
        verify(taskRepository).findById(taskDto.id());
    }

    @Test
    public void testDeleteTask() {
        Long taskId = 1L;
        Task task = new Task();
        TaskDeleteDto taskDeleteDto = new TaskDeleteDto(1L, 2L);
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));
        when(taskMapper.mapToDeleteDto(task)).thenReturn(taskDeleteDto);

        taskService.deleteTask(taskId);

        verify(taskRepository).deleteById(taskId);
    }

    @Test
    public void testMoveTask() {
        TaskMoveDto taskMoveDto = new TaskMoveDto(1L, 2L, 3L, 4L);
        Task task = new Task();
        task.setTaskList(new TaskList());
        Long previousTaskListId = 1L;
        when(taskRepository.findById(taskMoveDto.taskId())).thenReturn(Optional.of(task));
        when(taskRepository.save(any())).thenReturn(task);
        when(taskMapper.mapToMovedDto(task, previousTaskListId)).thenReturn(new TaskMovedDto(3L, "title",
                "description", false, Priority.LOW, LocalDateTime.now(), LocalDateTime.now(),
                4L, 1.0f, 4L));

        taskService.moveTask(taskMoveDto);

        verify(taskRepository).save(any());
        verify(taskRepository).findById(taskMoveDto.taskId());
    }
}