package com.smarthealth.health_monitoring.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patients")
@Data
@EqualsAndHashCode(callSuper = true)
public class Patient extends User {

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @OneToMany(mappedBy = "patient")
    private List<Sensor> sensors;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDate;
    private String gender;
    private Integer heightCm;
    private Integer weightKg;
    private String bloodType;
    private String allergies;
    private String chronicConditions;
    private String familyIllness;
    private String lifestyleSmoking;
    private String lifestyleAlcohol;
    private String activityLevel;
    private String sleepHoursAvg;
}