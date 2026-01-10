package com.smarthealth.health_monitoring.model.entity;

import com.smarthealth.health_monitoring.common.User;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "DOCTORS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Doctor extends User {

    @OneToMany(mappedBy = "doctor")
    private List<Patient> patients;

    @OneToMany(mappedBy = "doctor")
    private List<AlertRule> alertRules;

    @OneToMany(mappedBy = "doctor")
    private List<MedicalHistory> medicalHistoryList;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "license_number", nullable = false, unique = true)
    private String licenseNumber;
}
