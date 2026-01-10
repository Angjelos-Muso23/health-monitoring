package com.smarthealth.health_monitoring.model.domain.emergencyContact;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContactDomain {

  private String name;
  private String relationship;
  private String phoneNumber;
}
