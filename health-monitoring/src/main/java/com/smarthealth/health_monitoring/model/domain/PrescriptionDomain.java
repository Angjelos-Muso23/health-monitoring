package com.smarthealth.health_monitoring.model.domain;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDomain {

  private Long historyId;
  private String medicationName;
  private String dosage;
  private String frequency;
  private Date startDate;
  private Date endDate;
  private String instructions;
}
