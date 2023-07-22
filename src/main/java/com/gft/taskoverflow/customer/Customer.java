package com.gft.taskoverflow.customer;

import com.gft.taskoverflow.board.Board;
import com.gft.taskoverflow.task.list.TaskList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @NotEmpty
    @Size(max = 100)
    @Column(nullable = false)
    private String email;
    @NotEmpty
    @Size(max = 100)
    @Column(nullable = false)
    private String password;
    @ManyToMany(mappedBy = "customers")
    private Set<Board> boards;
    @ManyToOne(fetch = FetchType.LAZY)
    private TaskList taskList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        return id.equals(customer.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
