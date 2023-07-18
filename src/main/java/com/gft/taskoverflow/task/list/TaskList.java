package com.gft.taskoverflow.task.list;

import com.gft.taskoverflow.board.Board;
import com.gft.taskoverflow.customer.Customer;
import com.gft.taskoverflow.task.Task;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class TaskList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @OneToMany(mappedBy = "taskList")
    private Set<Task> tasks;
    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;
    @OneToMany(mappedBy = "taskList")
    private Set<Customer> assignedCustomers;
}
