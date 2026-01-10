package com.smarthealth.health_monitoring.model.entity;

import com.smarthealth.health_monitoring.common.AuditedEntity;
import com.smarthealth.health_monitoring.model.enums.MetricEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ALERT_RULES")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class AlertRule extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Enumerated(EnumType.STRING)
    @Column(name = "metric", nullable = false)
    private MetricEnum metric;

    @Column(name = "minValue")
    private Float minValue;

    @Column(name = "maxValue")
    private Float maxValue;

    @Column(name = "systolicMin")
    private Integer systolicMin;

    @Column(name = "systolicMax")
    private Integer systolicMax;

    @Column(name = "diastolicMin")
    private Integer diastolicMin;

    @Column(name = "diastolicMax")
    private Integer diastolicMax;
}
