package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.Alert;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRepository extends JpaRepository<Alert, Long> {

  List<Alert> findByPatient_Id(Long patientId);

  boolean existsByIdAndPatient_Email(Long alertId, String patientEmail);

  List<Alert> findAllByPatient_Id(Long patientId);
}
