package com.smarthealth.health_monitoring.model.entity;

import com.smarthealth.health_monitoring.common.AuditedEntity;
import com.smarthealth.health_monitoring.model.enums.MetricEnum;
import com.smarthealth.health_monitoring.model.enums.RiskLevelEnum;
import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ALERTS")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Alert extends AuditedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @OneToMany(mappedBy = "alert")
    private List<Notification> notificationList;

    @Enumerated(EnumType.STRING)
    @Column(name = "metric", nullable = false)
    private MetricEnum metric;

    @Column(name = "value")
    private Float value;

    @Column(name = "systolic")
    private Integer systolic;

    @Column(name = "diastolic")
    private Integer diastolic;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level", nullable = false)
    private RiskLevelEnum riskLevel;

    @Column(name = "message")
    private String message;

    @Column(name = "seen_by_doctor", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean seenByDoctor;
}
