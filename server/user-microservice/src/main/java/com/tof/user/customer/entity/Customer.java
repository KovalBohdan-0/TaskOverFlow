package com.tof.user.customer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer implements Serializable {
    @Id
    private String id;
    private String name;
    private String email;
    private String password;
    private boolean emailConfirmed;
    private boolean onEmailNotifications;
    private boolean onSiteNotifications;
}
