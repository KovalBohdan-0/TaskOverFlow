package com.gft.taskoverflow.board;

import com.gft.taskoverflow.customer.Customer;
import com.gft.taskoverflow.task.list.TaskList;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = 100)
    private String title;
    @OneToMany(mappedBy = "board")
    private Set<TaskList> taskLists;
    @ManyToMany
    private Set<Customer> customers;
}
