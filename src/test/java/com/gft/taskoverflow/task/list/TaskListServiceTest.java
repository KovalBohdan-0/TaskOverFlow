package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.board.Board;
import com.gft.taskoverflow.board.BoardService;
import com.gft.taskoverflow.task.list.dto.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskListServiceTest {
    @Mock
    private TaskListRepository taskListRepository;
    @Mock
    private TaskListMapper taskListMapper;
    @Mock
    private BoardService boardService;

    @InjectMocks
    private TaskListService taskListService;

    TaskListServiceTest() {
    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetBoardTaskListsResponse() {
        Long boardId = 1L;
        List<TaskList> mockTaskLists = new ArrayList<>();
        when(taskListRepository.findAllByBoardId(boardId)).thenReturn(mockTaskLists);

        List<TaskListResponseDto> result = taskListService.getBoardTaskListsResponse(boardId);

        assertNotNull(result);
    }

    @Test
    public void testGetTaskListById() {
        Long taskListId = 1L;
        TaskList mockTaskList = new TaskList();
        when(taskListRepository.findById(taskListId)).thenReturn(Optional.of(mockTaskList));

        TaskList result = taskListService.getTaskListById(taskListId);

        assertNotNull(result);
    }

    @Test
    public void testAddTaskList() {
        TaskListCreationDto taskListCreationDto = new TaskListCreationDto("Test title", 1L);
        when(taskListRepository.save(any())).thenReturn(new TaskList());
        when(taskListMapper.mapToTaskList(any())).thenReturn(new TaskList());
        when(boardService.getBoardById(1L)).thenReturn(new Board());

        taskListService.addTaskList(taskListCreationDto);

        verify(taskListRepository).save(any());
    }

    @Test
    public void testDeleteTaskList() {
        Long taskListId = 1L;

        Long result = taskListService.deleteTaskList(taskListId);

        assertEquals(taskListId, result);
    }

    @Test
    public void testRenameTaskList() {
        Long taskListId = 1L;
        TaskListRenameDto taskListRenameDto = new TaskListRenameDto(taskListId, "New Task List Title");
        TaskList mockTaskList = new TaskList();
        when(taskListRepository.findById(taskListId)).thenReturn(Optional.of(mockTaskList));

        TaskListRenameDto result = taskListService.renameTaskList(taskListRenameDto);

        assertNotNull(result);
    }

    @Test
    public void testMoveTaskList() {
        TaskListMoveDto taskListMoveDto = new TaskListMoveDto(1L, 2L, 3L);
        when(taskListRepository.findById(3L)).thenReturn(Optional.of(new TaskList()));

        taskListService.moveTaskList(taskListMoveDto);

        verify(taskListRepository).save(any());
    }

    @Test
    public void testUpdateTaskListSort() {
        TaskListUpdateSortDto taskListUpdateSortDto = new TaskListUpdateSortDto(1L, SortOption.CREATED_AT, SortDirection.ASC);
        when(taskListRepository.findById(1L)).thenReturn(Optional.of(new TaskList()));

        taskListService.updateTaskListSort(taskListUpdateSortDto);

        verify(taskListRepository).save(any());
    }

}