package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.MedicalHistory;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicalHistoryRepository extends JpaRepository<MedicalHistory, Long> {

  boolean existsByIdAndDoctor_Email(Long historyId, String email);

  List<MedicalHistory> findAllByPatient_Id(Long patientId);
}
