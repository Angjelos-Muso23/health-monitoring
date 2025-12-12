package com.smarthealth.health_monitoring.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "sensors")
@Data
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    private String sensorType;
    private String model;
    private Date pairedAt;
    private String status;
    private Date createdAt;
    private Date modifiedAt;
}
