package com.smarthealth.health_monitoring.model.entity;

import com.smarthealth.health_monitoring.common.AuditedEntity;
import com.smarthealth.health_monitoring.model.enums.MetricEnum;
import jakarta.persistence.*;
import java.util.Date;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "MEASUREMENTS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Measurement extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @Enumerated(EnumType.STRING)
    @Column(name = "metric", nullable = false)
    private MetricEnum metric;

    @Column(name = "value")
    private Float value;

    @Column(name = "systolic")
    private Integer systolic;

    @Column(name = "diastolic")
    private Integer diastolic;

    @Column(name = "measured_at", nullable = false)
    private Date measuredAt;
}
