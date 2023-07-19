package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.board.BoardService;
import com.gft.taskoverflow.exception.TaskListNotFoundException;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class TaskListService {
    private final TaskListRepository taskListRepository;
    private final BoardService boardService;

    public List<TaskList> getBoardTaskLists(Long boardId) {
        return taskListRepository.findAllByBoardId(boardId);
    }

    public TaskList getTaskListById(Long taskListId) {
        return taskListRepository.findById(taskListId).orElseThrow(() -> new TaskListNotFoundException(taskListId));
    }

    public void addTaskList(TaskListDto taskListDto) {
        TaskList taskList = new TaskList();
        taskList.setTitle(taskListDto.title());
        taskList.setBoard(boardService.getBoardById(taskListDto.boardId()));
        taskListRepository.save(taskList);
    }

    public void deleteTaskList(Long taskListId) {
        taskListRepository.deleteById(taskListId);
    }

    public void renameTaskList(Long taskListId, String title) {
        TaskList taskList = taskListRepository.findById(taskListId).orElseThrow(() -> new TaskListNotFoundException(taskListId));
        taskList.setTitle(title);
        taskListRepository.save(taskList);
    }
}
