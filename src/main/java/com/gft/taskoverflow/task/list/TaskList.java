package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.board.Board;
import com.gft.taskoverflow.customer.Customer;
import com.gft.taskoverflow.task.Task;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false,length = 100)
    private String title;
    private Float position;
    @Enumerated(EnumType.STRING)
    private SortOption sortOption = SortOption.POSITION;
    @Enumerated(EnumType.STRING)
    private SortDirection sortDirection = SortDirection.ASC;
    @OneToMany(mappedBy = "taskList", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks;
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
    @ManyToMany(mappedBy = "taskLists")
    private Set<Customer> assignedCustomers;

    public TaskList () {
    }

    public TaskList (String title, Long boardId) {
        this.title = title;
        this.board = new Board(boardId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TaskList taskList = (TaskList) o;

        return id.equals(taskList.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
