package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.PrescriptionDomain;
import java.util.List;

public interface PrescriptionService {
  PrescriptionDomain createPrescription(Long historyId, PrescriptionDomain request);

  List<PrescriptionDomain> getPrescriptionsByPatient(Long patientId);
}
