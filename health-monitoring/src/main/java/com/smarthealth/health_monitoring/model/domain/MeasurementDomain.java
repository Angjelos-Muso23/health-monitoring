package com.smarthealth.health_monitoring.model.domain;

import com.smarthealth.health_monitoring.model.enums.MetricEnum;
import jakarta.persistence.*;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDomain {

  private Long patientId;
  private Long sensorId;
  private MetricEnum metric;
  private Float value;
  private Integer systolic;
  private Integer diastolic;
  private Date measuredAt;
}
