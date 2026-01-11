package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.AlertDomain;
import com.smarthealth.health_monitoring.model.entity.Measurement;
import java.util.List;

public interface AlertService {
  List<AlertDomain> getAlertsByPatient(Long patientId);

  AlertDomain getAlertById(Long alertId);

  void evaluate(Measurement saved);
}
