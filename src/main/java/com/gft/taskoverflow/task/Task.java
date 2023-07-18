package com.gft.taskoverflow.task;

import com.gft.taskoverflow.task.list.TaskList;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(length = 1000)
    private String description;
    private boolean done;
    private Priority priority = Priority.LOW;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime deadline;
    @ManyToOne(fetch = FetchType.LAZY)
    private TaskList taskList;
}
