package com.gft.taskoverflow.task.list;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TaskListRepository extends JpaRepository<TaskList, Long> {
    List<TaskList> findAllByBoardId(Long boardId);
    @Query("SELECT MAX(t.position) FROM TaskList t WHERE t.board.id = ?1")
    Optional<Float> findMaxPositionByBoardId(Long boardId);
}
