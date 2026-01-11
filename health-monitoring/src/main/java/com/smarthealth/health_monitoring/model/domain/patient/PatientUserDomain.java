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
public class PatientUserDomain {

  private String email;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private LocalDate birthDate;
  private String gender;
}
