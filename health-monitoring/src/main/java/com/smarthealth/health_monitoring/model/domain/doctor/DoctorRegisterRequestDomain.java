package com.smarthealth.health_monitoring.model.domain.doctor;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorRegisterRequestDomain {

  private String email;
  private String password;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private LocalDate birthDate;
  private String gender;
  private String specialization;
  private String licenseNumber;
}
