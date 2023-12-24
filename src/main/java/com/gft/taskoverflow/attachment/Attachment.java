package com.gft.taskoverflow.attachment;

import com.gft.taskoverflow.task.Task;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Attachment {
    @Id
    @Column(name = "task_id")
    private Long id;
    private String name;
    private Long size;
    @OneToOne
    @MapsId
    @JoinColumn(name = "task_id")
    private Task task;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Attachment attachment = (Attachment) o;

        return id.equals(attachment.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
