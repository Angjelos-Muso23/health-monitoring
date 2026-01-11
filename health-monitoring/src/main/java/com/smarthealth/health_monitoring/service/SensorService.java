package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.SensorDomain;
import java.util.List;

public interface SensorService {
  SensorDomain addSensor(Long patientId, SensorDomain request);

  List<SensorDomain> getSensorsByPatient(Long patientId);

  void deleteSensor(Long sensorId);
}
