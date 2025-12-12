package com.smarthealth.health_monitoring.model.domain;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PatientDomain {

    private String email;
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
    private Long doctorId;
}