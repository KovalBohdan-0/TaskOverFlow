package com.gft.taskoverflow.board;

import com.gft.taskoverflow.customer.Customer;
import com.gft.taskoverflow.task.list.TaskList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @NotEmpty
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String title;
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<TaskList> taskLists;
    @ManyToMany
    private Set<Customer> customers;

    public Board () {
    }

    public Board (String title) {
        this.title = title;
    }

    public Board (Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return id.equals(board.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
