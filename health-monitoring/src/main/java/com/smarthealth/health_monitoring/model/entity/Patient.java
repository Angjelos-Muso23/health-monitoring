package com.smarthealth.health_monitoring.model.entity;

import com.smarthealth.health_monitoring.common.User;
import com.smarthealth.health_monitoring.model.enums.BloodTypeEnum;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "PATIENTS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Patient extends User {

    //    @PostLoad
    //    @PostPersist
    //    private void setRole() {
    //        super.setRole(UserRoleEnum.ROLE_PATIENT);
    //    }

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @OneToMany(mappedBy = "patient")
    private List<Sensor> sensors;

    @OneToMany(mappedBy = "patient")
    private List<Alert> alerts;

    @OneToOne(mappedBy = "patient")
    private AlertRule alertRule;

    @OneToMany(mappedBy = "patient")
    private List<EmergencyContact> emergencyContacts;

    @OneToMany(mappedBy = "patient")
    private List<Measurement> measurements;

    @OneToMany(mappedBy = "patient")
    private List<MedicalHistory> medicalHistoryList;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "height_cm")
    private Integer heightCm;

    @Column(name = "weight_kg")
    private Integer weightKg;

    @Enumerated(EnumType.STRING)
    @Column(name = "blood_type")
    private BloodTypeEnum bloodType;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "chronic_conditions")
    private String chronicConditions;

    @Column(name = "family_illness")
    private String familyIllness;

    @Column(name = "lifestyle_smoking")
    private String lifestyleSmoking;

    @Column(name = "lifestyle_alcohol")
    private String lifestyleAlcohol;

    @Column(name = "activity_level")
    private String activityLevel;

    @Column(name = "sleep_hours_avg")
    private String sleepHoursAvg;
}
