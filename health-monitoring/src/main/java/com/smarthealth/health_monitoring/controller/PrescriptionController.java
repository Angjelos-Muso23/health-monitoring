package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.PrescriptionDomain;
import com.smarthealth.health_monitoring.service.PrescriptionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prescriptions")
@RequiredArgsConstructor
public class PrescriptionController {

  private final PrescriptionService prescriptionService;

  @PreAuthorize(
      "hasAuthority('ROLE_DOCTOR' and @securityService.isMedicalHistoryDoctor(#historyId))")
  @PostMapping("/patients/{historyId}")
  public ResponseEntity<PrescriptionDomain> createPrescription(
      @PathVariable Long historyId, @RequestBody PrescriptionDomain request) {
    return ResponseEntity.ok(prescriptionService.createPrescription(historyId, request));
  }

  @PreAuthorize(
      "hasAuthority('ROLE_DOCTOR' and @securityService.isFamilyDoctor(#patientId)) or hasAuthority('ROLE_PATIENT' and @securityService.isPatientSelf(#patientId))")
  @GetMapping("/patients/{patientId}")
  public ResponseEntity<List<PrescriptionDomain>> getPrescriptions(@PathVariable Long patientId) {
    return ResponseEntity.ok(prescriptionService.getPrescriptionsByPatient(patientId));
  }
}
