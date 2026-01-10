package com.smarthealth.health_monitoring.model.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertDomain {

  private Long patientId;
  private String metric;
  private Float value;
  private Integer systolic;
  private Integer diastolic;
  private String level;
  private String message;
  private Boolean seenByDoctor;
}
