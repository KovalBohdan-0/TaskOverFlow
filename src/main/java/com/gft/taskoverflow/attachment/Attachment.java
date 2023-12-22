package com.gft.taskoverflow.attachment;

import com.gft.taskoverflow.customer.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    @Id
    @Column(name = "customer_id")
    private Long id;
    private String name;
    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

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
