package com.gft.taskoverflow.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT MAX(t.position) FROM Task t WHERE t.taskList.id = ?1")
    Optional<Float> findMaxPositionByTaskListId(Long taskListId);
}
