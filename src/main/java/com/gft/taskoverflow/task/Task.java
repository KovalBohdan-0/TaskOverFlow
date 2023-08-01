package com.gft.taskoverflow.task;

import com.gft.taskoverflow.notification.Notification;
import com.gft.taskoverflow.task.list.TaskList;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @Column(length = 1000)
    private String description = "";
    private Float position;
    private boolean done;
    private Priority priority = Priority.LOW;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime deadline;
    @ManyToOne(fetch = FetchType.LAZY)
    private TaskList taskList;
    @OneToOne(cascade = CascadeType.ALL)
    private Notification notification;

    public Task () {
    }

    public Task (String title, Priority priority) {
        this.title = title;
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        return id.equals(task.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", position=" + position +
                ", done=" + done +
                ", priority=" + priority +
                ", createdAt=" + createdAt +
                ", deadline=" + deadline +
                ", taskList=" + taskList.getId() +
                '}';
    }
}
