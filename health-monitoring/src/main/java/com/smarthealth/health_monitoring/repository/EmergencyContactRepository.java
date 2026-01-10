package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactDomain;
import com.smarthealth.health_monitoring.model.entity.EmergencyContact;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {

  boolean existsByIdAndPatient_Email(Long id, String patientEmail);

  List<EmergencyContactDomain> findAllByPatient_Id(Long patientId);
}
