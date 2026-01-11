package com.smarthealth.health_monitoring.model.domain.patient;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientUpdateRequestDomain {
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
  private String doctorEmail;
}
