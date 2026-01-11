package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.AlertRuleDomain;

public interface AlertRuleService {
  AlertRuleDomain createRule(Long patientId, AlertRuleDomain request);

  AlertRuleDomain getRulesByPatient(Long patientId);

  AlertRuleDomain updateRule(Long ruleId, AlertRuleDomain request);

  void deleteRule(Long ruleId);
}
