package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.MedicalHistoryDomain;
import com.smarthealth.health_monitoring.service.MedicalHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/medical-history")
@RequiredArgsConstructor
public class MedicalHistoryController {

  private final MedicalHistoryService medicalHistoryService;

  @PreAuthorize("hasAuthority('ROLE_DOCTOR' and @securityService.isFamilyDoctor(#patientId))")
  @PostMapping("/patients/{patientId}")
  public ResponseEntity<MedicalHistoryDomain> createMedicalHistory(
      @PathVariable Long patientId, @RequestBody MedicalHistoryDomain request) {
    return ResponseEntity.ok(medicalHistoryService.createMedicalHistory(patientId, request));
  }

  @PreAuthorize(
      "hasAuthority('ROLE_DOCTOR' and @securityService.isFamilyDoctor(#patientId)) or hasAuthority('ROLE_PATIENT' and @securityService.isPatientSelf(#patientId))")
  @GetMapping("/patients/{patientId}")
  public ResponseEntity<List<MedicalHistoryDomain>> getPatientMedicalHistory(
      @PathVariable Long patientId) {
    return ResponseEntity.ok(medicalHistoryService.getMedicalHistoryByPatient(patientId));
  }
}
