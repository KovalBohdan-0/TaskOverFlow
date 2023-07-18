package com.gft.taskoverflow.customer;

import com.gft.taskoverflow.board.Board;
import com.gft.taskoverflow.task.list.TaskList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @ManyToMany(mappedBy = "customers")
    private Set<Board> boards;
    @ManyToOne(fetch = FetchType.LAZY)
    private TaskList taskList;
}
