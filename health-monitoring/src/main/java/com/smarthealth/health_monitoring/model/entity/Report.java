package com.smarthealth.health_monitoring.model.entity;

import com.smarthealth.health_monitoring.common.AuditedEntity;
import com.smarthealth.health_monitoring.model.enums.ReportTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "REPORTS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Report extends AuditedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "patient_id", nullable = false)
  private Patient patient;

  @ManyToOne
  @JoinColumn(name = "doctor_id", nullable = false)
  private Doctor doctor;

  @Enumerated(EnumType.STRING)
  @Column(name = "report_type", nullable = false)
  private ReportTypeEnum reportType;

  @Column(name = "pdfPath", nullable = false)
  private String pdfPath;
}
