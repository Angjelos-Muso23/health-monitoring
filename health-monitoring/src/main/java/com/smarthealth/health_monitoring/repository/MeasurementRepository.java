package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.Measurement;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementRepository extends JpaRepository<Measurement, Long> {
  List<Measurement> findAllByPatient_Id(Long patientId);

  Optional<Measurement> findTopByPatient_IdOrderByCreateDateDesc(Long patientId);
}
