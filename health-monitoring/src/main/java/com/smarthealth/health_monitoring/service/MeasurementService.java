package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.MeasurementDomain;
import java.util.List;

public interface MeasurementService {
  MeasurementDomain createMeasurement(MeasurementDomain request);

  List<MeasurementDomain> getMeasurementsByPatient(Long patientId);

  MeasurementDomain getLatestMeasurement(Long patientId);
}
