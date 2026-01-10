package com.smarthealth.health_monitoring.model.domain;

import com.smarthealth.health_monitoring.model.enums.ReportTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDomain {

  private Long patientId;
  private Long doctorId;
  private ReportTypeEnum reportType;
  private String pdfPath;
}
