package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}