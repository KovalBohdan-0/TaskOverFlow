package com.gft.taskoverflow.customer;

import com.gft.taskoverflow.attachment.Attachment;
import com.gft.taskoverflow.attachment.UserAttachmentStats;
import com.gft.taskoverflow.board.Board;
import com.gft.taskoverflow.task.list.TaskList;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @SequenceGenerator(
            name = "customer_id_sequence",
            sequenceName = "customer_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_sequence"
    )
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
    @Column(nullable = false)
    private boolean emailConfirmed = false;
    @Column(nullable = false)
    private boolean onEmailNotifications = false;
    @Column(nullable = false)
    private boolean onSiteNotifications = true;
    @ManyToMany(mappedBy = "customers")
    private Set<Board> boards;
    @ManyToMany
    private List<TaskList> taskLists;
    @OneToOne(mappedBy = "customer")
    private UserAttachmentStats userAttachmentStats;
    @OneToOne(mappedBy = "customer")
    private Attachment attachment;

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
