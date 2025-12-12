package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
}
