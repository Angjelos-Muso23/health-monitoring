package com.smarthealth.health_monitoring.model.domain;

import com.smarthealth.health_monitoring.model.entity.Prescription;
import com.smarthealth.health_monitoring.model.enums.RiskLevelEnum;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicalHistoryDomain {

  private Long doctorId;
  private List<Prescription> prescriptions;
  private String diagnosis;
  private String doctorNotes;
  private RiskLevelEnum riskLevel;
  private String treatmentPlan;
}
