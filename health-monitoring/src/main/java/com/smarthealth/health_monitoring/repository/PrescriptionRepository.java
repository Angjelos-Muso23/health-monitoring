package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.Prescription;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

  List<Prescription> findAllByMedicalHistory_Patient_Id(Long patientId);
}
