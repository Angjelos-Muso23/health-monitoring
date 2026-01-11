package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.AlertRuleMapper;
import com.smarthealth.health_monitoring.model.domain.AlertRuleDomain;
import com.smarthealth.health_monitoring.model.entity.AlertRule;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.AlertRuleRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.AlertRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlertRuleServiceImpl implements AlertRuleService {

  private final AlertRuleRepository alertRuleRepository;
  private final PatientRepository patientRepository;
  private final AlertRuleMapper alertRuleMapper;

  public AlertRuleDomain createRule(Long patientId, AlertRuleDomain request) {
    Patient patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    AlertRule alertRule = alertRuleMapper.toEntity(request);
    alertRule.setPatient(patient);
    AlertRule savedRule = alertRuleRepository.save(alertRule);
    return alertRuleMapper.toDomain(savedRule);
  }

  public AlertRuleDomain getRulesByPatient(Long patientId) {
    if (!patientRepository.existsById(patientId)) {
      throw new ResourceNotFoundException("Patient not found");
    }
    AlertRule alertRule =
        alertRuleRepository
            .findByPatient_Id(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Alert rules not found for patient"));
    return alertRuleMapper.toDomain(alertRule);
  }

  public AlertRuleDomain updateRule(Long ruleId, AlertRuleDomain request) {
    AlertRule alertRule =
        alertRuleRepository
            .findById(ruleId)
            .orElseThrow(() -> new ResourceNotFoundException("Alert rule not found"));
    alertRuleMapper.updateEntityFromDomain(request, alertRule);
    alertRuleRepository.save(alertRule);
    return alertRuleMapper.toDomain(alertRule);
  }

  public void deleteRule(Long ruleId) {
    if (!alertRuleRepository.existsById(ruleId)) {
      throw new ResourceNotFoundException("Alert rule not found");
    }
    alertRuleRepository.deleteById(ruleId);
  }
}
