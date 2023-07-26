package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.board.BoardService;
import com.gft.taskoverflow.customer.CustomerService;
import com.gft.taskoverflow.exception.TaskListNotFoundException;
import com.gft.taskoverflow.task.list.dto.TaskListCreationDto;
import com.gft.taskoverflow.task.list.dto.TaskListRenameDto;
import com.gft.taskoverflow.task.list.dto.TaskListResponseDto;
import lombok.*;
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
    private final CustomerService customerService;

    public List<TaskListResponseDto> getBoardTaskListsResponse(Long boardId) {
        return taskListRepository.findAllByBoardId(boardId).stream().map(taskListMapper::mapToResponseDto).toList();
    }

    public TaskList getTaskListById(Long taskListId) {
        return taskListRepository.findById(taskListId).orElseThrow(() -> new TaskListNotFoundException(taskListId));
    }

    public TaskListResponseDto addTaskList(TaskListCreationDto taskList) {
        TaskList newTaskList = taskListMapper.mapToTaskList(taskList);
        newTaskList.setBoard(boardService.getBoardById(taskList.boardId()));
        taskListRepository.save(newTaskList);
        return taskListMapper.mapToResponseDto(newTaskList);
    }

    public Long deleteTaskList(Long taskListId) {
        taskListRepository.deleteById(taskListId);
        return taskListId;
    }

    public TaskListRenameDto renameTaskList(TaskListRenameDto renamedTaskList) {
        TaskList taskList = taskListRepository.findById(renamedTaskList.taskListId()).orElseThrow(() -> new TaskListNotFoundException(renamedTaskList.taskListId()));
        taskList.setTitle(renamedTaskList.title());
        taskListRepository.save(taskList);
        return renamedTaskList;
    }

    public void checkCustomersBoard(Long boardId) {
        if (!customerService.currentCustomerContainsBoard(boardId)) {
            throw new TaskListNotFoundException(boardId);
        }
    }
}
