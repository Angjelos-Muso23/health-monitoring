package com.smarthealth.health_monitoring.model.entity;

import com.smarthealth.health_monitoring.common.AuditedEntity;
import com.smarthealth.health_monitoring.model.enums.SensorTypeEnum;
import jakarta.persistence.*;
import java.util.Date;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "SENSORS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Sensor extends AuditedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "patient_id", nullable = false)
  private Patient patient;

  @OneToMany(mappedBy = "sensor")
  private List<Measurement> measurements;

  @Enumerated(EnumType.STRING)
  @Column(name = "sensor_type", nullable = false)
  private SensorTypeEnum sensorType;

  @Column(name = "model")
  private String model;

  @Column(name = "paired_at", nullable = false)
  private Date pairedAt;

  @Enumerated(EnumType.STRING)
  @Column(name = "status", nullable = false)
  private SensorTypeEnum status;
}
