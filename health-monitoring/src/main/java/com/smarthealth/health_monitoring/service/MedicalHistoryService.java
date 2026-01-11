package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.MedicalHistoryDomain;
import java.util.List;

public interface MedicalHistoryService {
  MedicalHistoryDomain createMedicalHistory(Long patientId, MedicalHistoryDomain request);

  List<MedicalHistoryDomain> getMedicalHistoryByPatient(Long patientId);
}
