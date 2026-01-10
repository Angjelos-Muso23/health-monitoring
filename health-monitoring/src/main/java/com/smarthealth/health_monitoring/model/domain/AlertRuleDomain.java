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
public class AlertRuleDomain {

  private Long patientId;
  private Long doctorId;
  private String metric;
  private Float minValue;
  private Float maxValue;
  private Integer systolicMin;
  private Integer systolicMax;
  private Integer diastolicMin;
  private Integer diastolicMax;
}
