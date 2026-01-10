package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.Patient;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {

  Optional<Patient> findByEmail(String email);

  boolean existsByIdAndEmail(Long patientId, String email);

  boolean existsByIdAndDoctor_Email(Long patientId, String email);

  List<Patient> findAllByDoctor_Id(Long doctorId);
}
