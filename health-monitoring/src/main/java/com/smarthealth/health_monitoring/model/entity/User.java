package com.smarthealth.health_monitoring.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@MappedSuperclass
@Data
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String passwordHash;
    private Date createdAt;
    private Date modifiedAt;
}
