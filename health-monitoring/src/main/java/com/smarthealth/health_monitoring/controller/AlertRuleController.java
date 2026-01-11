package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.AlertRuleDomain;
import com.smarthealth.health_monitoring.service.AlertRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alert-rules")
@RequiredArgsConstructor
public class AlertRuleController {

  private final AlertRuleService alertRuleService;

  @PreAuthorize("hasAuthority('ROLE_DOCTOR' and @securityService.isFamilyDoctor(#patientId))")
  @PostMapping("/patients/{patientId}")
  public ResponseEntity<AlertRuleDomain> createRule(
      @PathVariable Long patientId, @RequestBody AlertRuleDomain request) {
    return ResponseEntity.ok(alertRuleService.createRule(patientId, request));
  }

  @PreAuthorize(
      "hasAuthority('ROLE_DOCTOR' and @securityService.isFamilyDoctor(#patientId)) or hasAuthority('ROLE_PATIENT' and @securityService.isPatientSelf(#patientId))")
  @GetMapping("/patients/{patientId}")
  public ResponseEntity<AlertRuleDomain> getPatientRule(@PathVariable Long patientId) {
    return ResponseEntity.ok(alertRuleService.getRulesByPatient(patientId));
  }

  @PreAuthorize("hasAuthority('ROLE_DOCTOR' and @securityService.isFamilyDoctor(#patientId))")
  @PutMapping("/{ruleId}")
  public ResponseEntity<AlertRuleDomain> updateRule(
      @PathVariable Long ruleId, @RequestBody AlertRuleDomain request) {
    return ResponseEntity.ok(alertRuleService.updateRule(ruleId, request));
  }

  @PreAuthorize("hasAuthority('ROLE_DOCTOR' and @securityService.isFamilyDoctor(#patientId))")
  @DeleteMapping("/{ruleId}")
  public ResponseEntity<Void> deleteRule(@PathVariable Long ruleId) {
    alertRuleService.deleteRule(ruleId);
    return ResponseEntity.noContent().build();
  }
}
