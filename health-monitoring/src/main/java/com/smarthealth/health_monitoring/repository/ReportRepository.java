package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.Report;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
  List<Report> findAllByPatient_Id(Long patientId);
}
