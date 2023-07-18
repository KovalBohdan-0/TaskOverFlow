package com.gft.taskoverflow.task.list;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findAllByBoardId(Long boardId);
}
