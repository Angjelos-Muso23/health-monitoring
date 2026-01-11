package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.MeasurementDomain;
import com.smarthealth.health_monitoring.service.MeasurementService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/measurements")
@RequiredArgsConstructor
public class MeasurementController {

  private final MeasurementService measurementService;

  @PostMapping
  public ResponseEntity<MeasurementDomain> createMeasurement(
      @RequestBody MeasurementDomain request) {
    return ResponseEntity.ok(measurementService.createMeasurement(request));
  }

  @PreAuthorize(
      "(hasAuthority('ROLE_PATIENT') and @securityService.isPatientSelf(#patientId)) or (hasAuthority('ROLE_DOCTOR') and @securityService.isFamilyDoctor(#patientId))")
  @GetMapping("/patients/{patientId}")
  public ResponseEntity<List<MeasurementDomain>> getPatientMeasurements(
      @PathVariable Long patientId) {
    return ResponseEntity.ok(measurementService.getMeasurementsByPatient(patientId));
  }

  @PreAuthorize(
      "(hasAuthority('ROLE_PATIENT') and @securityService.isPatientSelf(#patientId)) or (hasAuthority('ROLE_DOCTOR') and @securityService.isFamilyDoctor(#patientId))")
  @GetMapping("/patients/{patientId}/latest")
  public ResponseEntity<MeasurementDomain> getLatestMeasurement(@PathVariable Long patientId) {
    return ResponseEntity.ok(measurementService.getLatestMeasurement(patientId));
  }
}
