package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.AlertDomain;
import com.smarthealth.health_monitoring.service.AlertService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alerts")
@RequiredArgsConstructor
public class AlertController {

  private final AlertService alertService;

  @PreAuthorize(
      "(hasAuthority('ROLE_DOCTOR') and @securityService.isFamilyDoctor(#patientId)) or (hasAuthority('ROLE_PATIENT') and @securityService.isPatientSelf(#patientId))")
  @GetMapping("/patients/{patientId}")
  public ResponseEntity<List<AlertDomain>> getPatientAlerts(@PathVariable Long patientId) {
    return ResponseEntity.ok(alertService.getAlertsByPatient(patientId));
  }

  @PreAuthorize("hasAuthority('ROLE_PATIENT') and @securityService.isOwnerOfAlert(#alertId)")
  @GetMapping("/{alertId}")
  public ResponseEntity<AlertDomain> getAlert(@PathVariable Long alertId) {
    return ResponseEntity.ok(alertService.getAlertById(alertId));
  }
}
