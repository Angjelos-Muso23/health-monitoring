package com.smarthealth.health_monitoring.model.entity;

import com.smarthealth.health_monitoring.common.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "doctors")
@Data
@EqualsAndHashCode(callSuper = true)
public class Doctor extends User {

    @OneToMany(mappedBy = "doctor")
    private List<Patient> patients;

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String specialization;
    private String licenseNumber;
}