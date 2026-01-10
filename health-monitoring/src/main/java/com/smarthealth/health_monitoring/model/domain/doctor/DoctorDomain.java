package com.smarthealth.health_monitoring.model.domain.doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDomain {

  private String email;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private String specialization;
  private String licenseNumber;
}
