package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.ReportDomain;
import com.smarthealth.health_monitoring.service.ReportService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

  private final ReportService reportService;

  @PreAuthorize("hasAuthority('ROLE_DOCTOR' and @securityService.isFamilyDoctor(#patientId))")
  @PostMapping("/patients/{patientId}")
  public ResponseEntity<ReportDomain> generateReport(
      @PathVariable Long patientId, @RequestBody ReportDomain request) {
    return ResponseEntity.ok(reportService.generateReport(patientId, request));
  }

  @PreAuthorize(
      "hasAuthority('ROLE_DOCTOR' and @securityService.isFamilyDoctor(#patientId)) or hasAuthority('ROLE_PATIENT' and @securityService.isPatientSelf(#patientId))")
  @GetMapping("/patients/{patientId}")
  public ResponseEntity<List<ReportDomain>> getPatientReports(@PathVariable Long patientId) {
    return ResponseEntity.ok(reportService.getReportsByPatient(patientId));
  }
}
