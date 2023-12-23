package com.gft.taskoverflow.attachment;

import com.gft.taskoverflow.customer.Customer;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class UserAttachmentStats {
    @Id
    @Column(name = "customer_id")
    private Long id;
    private int attachmentCount;
    private int uploadCountByDay;
    private LocalDate lastUploadDate;
    private int downloadCountByDay;
    private LocalDate lastDownloadDate;
    @OneToOne
    @MapsId
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserAttachmentStats userAttachmentStats = (UserAttachmentStats) o;

        return id.equals(userAttachmentStats.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
