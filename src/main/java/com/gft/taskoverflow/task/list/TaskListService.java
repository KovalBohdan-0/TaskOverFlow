package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.board.BoardService;
import com.gft.taskoverflow.customer.CustomerService;
import com.gft.taskoverflow.exception.TaskListNotFoundException;
import com.gft.taskoverflow.task.list.dto.*;
import lombok.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
        newTaskList.setPosition(taskListRepository.findMaxPositionByBoardId(taskList.boardId()).orElse(0.0f) + 1);
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

    @Transactional(propagation = Propagation.REQUIRED)
    public TaskListResponseDto moveTaskList(TaskListMoveDto taskListMoveDto) {
        TaskList taskList = getTaskListById(taskListMoveDto.taskListId());
        Optional<TaskList> taskListBefore = taskListRepository.findById(taskListMoveDto.taskListBeforeId());
        Optional<TaskList> taskListAfter = taskListRepository.findById(taskListMoveDto.taskListAfterId());

        if (taskListAfter.isPresent() && taskListBefore.isPresent()) {
            taskList.setPosition((taskListBefore.get().getPosition() + taskListAfter.get().getPosition()) / 2);
        } else if (taskListAfter.isPresent()) {
            taskList.setPosition(taskListAfter.get().getPosition() - 1);
        } else taskListBefore.ifPresent(list -> taskList.setPosition(list.getPosition() + 1));

        return taskListMapper.mapToResponseDto(taskListRepository.save(taskList));
    }

    public TaskListResponseDto updateTaskListSort(TaskListUpdateSortDto taskListUpdateSortDto) {
        TaskList taskList = getTaskListById(taskListUpdateSortDto.id());
        taskList.setSortOption(taskListUpdateSortDto.sortOption());
        taskList.setSortDirection(taskListUpdateSortDto.sortDirection());
        return taskListMapper.mapToResponseDto(taskListRepository.save(taskList));
    }
}
