package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.board.BoardService;
import com.gft.taskoverflow.exception.TaskListNotFoundException;
import lombok.Data;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class TaskListService {
    private final TaskListRepository taskListRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final TaskListMapper taskListMapper;
    private final BoardService boardService;

    public List<TaskList> getBoardTaskLists(Long boardId) {
        return taskListRepository.findAllByBoardId(boardId);
    }

    public List<TaskListResponseDto> getBoardTaskListsResponse(Long boardId) {
        return taskListRepository.findAllByBoardId(boardId).stream().map(taskListMapper::mapToResponseDto).toList();
    }

    public TaskList getTaskListById(Long taskListId) {
        return taskListRepository.findById(taskListId).orElseThrow(() -> new TaskListNotFoundException(taskListId));
    }

    public void addTaskList(TaskList taskList, Long boardId) {
        taskList.setBoard(boardService.getBoardById(boardId));
        taskListRepository.save(taskList);

        messagingTemplate.convertAndSend("/topic/task-list-added", taskListMapper.mapToResponseDto(taskList));
    }

    public void deleteTaskList(Long taskListId) {
        taskListRepository.deleteById(taskListId);

        messagingTemplate.convertAndSend("/topic/task-list-removed", taskListId);
    }

    public void renameTaskList(Long taskListId, String title) {
        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(() -> new TaskListNotFoundException(taskListId));
        taskList.setTitle(title);
        taskListRepository.save(taskList);

        messagingTemplate.convertAndSend("/topic/task-list-renamed", title);
    }
}
