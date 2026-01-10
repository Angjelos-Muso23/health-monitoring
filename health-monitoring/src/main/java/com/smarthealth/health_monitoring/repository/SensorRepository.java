package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.Sensor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

  boolean existsByIdAndPatient_Email(Long sensorId, String patientEmail);

  List<Sensor> findAllByPatient_Id(Long patientId);
}
