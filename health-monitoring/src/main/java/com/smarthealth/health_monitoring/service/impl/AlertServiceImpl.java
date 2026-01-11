package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.AlertMapper;
import com.smarthealth.health_monitoring.model.domain.AlertDomain;
import com.smarthealth.health_monitoring.model.entity.Alert;
import com.smarthealth.health_monitoring.model.entity.AlertRule;
import com.smarthealth.health_monitoring.model.entity.Measurement;
import com.smarthealth.health_monitoring.model.enums.RiskLevelEnum;
import com.smarthealth.health_monitoring.repository.AlertRepository;
import com.smarthealth.health_monitoring.repository.AlertRuleRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.AlertService;
import com.smarthealth.health_monitoring.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertServiceImpl implements AlertService {

  private final PatientRepository patientRepository;
  private final AlertRepository alertRepository;
  private final AlertMapper alertMapper;
  private final AlertRuleRepository alertRuleRepository;
  private final NotificationService notificationService;

  public List<AlertDomain> getAlertsByPatient(Long patientId) {
    if (!patientRepository.existsById(patientId)) {
      throw new ResourceNotFoundException("Patient not found");
    }
    List<Alert> alerts = alertRepository.findAllByPatient_Id(patientId);
    return alerts.stream().map(alertMapper::toDomain).toList();
  }

  public AlertDomain getAlertById(Long alertId) {
    Alert alert =
        alertRepository
            .findById(alertId)
            .orElseThrow(() -> new ResourceNotFoundException("Alert not found"));
    return alertMapper.toDomain(alert);
  }

  public void evaluate(Measurement measurement) {
    AlertRule rule =
        alertRuleRepository
            .findByPatient_Id(measurement.getPatient().getId())
            .orElseThrow(() -> new ResourceNotFoundException("AlertRule not found"));

    boolean violated =
        (rule.getMinValue() != null && measurement.getValue() < rule.getMinValue())
            || (rule.getMaxValue() != null && measurement.getValue() > rule.getMaxValue()
                || (rule.getDiastolicMax() != null
                    && measurement.getValue() > rule.getDiastolicMax())
                || (rule.getDiastolicMin() != null
                    && measurement.getValue() < rule.getDiastolicMin()));

    if (violated) {
      createAlert(measurement, rule);
    }
  }

  private void createAlert(Measurement measurement, AlertRule rule) {
    RiskLevelEnum riskLevel;

    if (Math.abs(measurement.getValue() - rule.getMinValue()) > 30
        || Math.abs(measurement.getValue() - rule.getMaxValue()) > 30
        || (rule.getDiastolicMax() != null
            && Math.abs(measurement.getDiastolic() - rule.getDiastolicMax()) > 3)
        || (rule.getDiastolicMin() != null
            && Math.abs(measurement.getDiastolic() - rule.getDiastolicMin()) > 3)) {
      riskLevel = RiskLevelEnum.CRITICAL;
    } else if (Math.abs(measurement.getValue() - rule.getMinValue()) > 20
        || Math.abs(measurement.getValue() - rule.getMaxValue()) > 20
        || (rule.getDiastolicMax() != null
            && Math.abs(measurement.getDiastolic() - rule.getDiastolicMax()) > 2)
        || (rule.getDiastolicMin() != null
            && Math.abs(measurement.getDiastolic() - rule.getDiastolicMin()) > 2)) {
      riskLevel = RiskLevelEnum.HIGH;
    } else if (Math.abs(measurement.getValue() - rule.getMinValue()) > 10
        || Math.abs(measurement.getValue() - rule.getMaxValue()) > 10
        || (rule.getDiastolicMax() != null
            && Math.abs(measurement.getDiastolic() - rule.getDiastolicMax()) > 1)
        || (rule.getDiastolicMin() != null
            && Math.abs(measurement.getDiastolic() - rule.getDiastolicMin()) > 1)) {
      riskLevel = RiskLevelEnum.MEDIUM;
    } else {
      riskLevel = RiskLevelEnum.LOW;
    }

    Alert alert =
        Alert.builder()
            .patient(measurement.getPatient())
            .metric(measurement.getMetric())
            .value(measurement.getValue())
            .diastolic(measurement.getDiastolic())
            .systolic(measurement.getSystolic())
            .riskLevel(riskLevel)
            .message("Patient needs attention: Problem with " + measurement.getMetric())
            .build();

    Alert savedAlert = alertRepository.save(alert);

    notificationService.notifyForAlert(alertMapper.toDomain(savedAlert));
  }
}
