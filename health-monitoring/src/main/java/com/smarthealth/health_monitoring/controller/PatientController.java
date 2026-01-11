package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientUpdateRequestDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientUserDomain;
import com.smarthealth.health_monitoring.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

  private final PatientService patientService;

  @PreAuthorize(
      "hasAuthority('ROLE_PATIENT') and @securityService.isPatientSelf(#id) or hasAuthority('ROLE_DOCTOR') and @securityService.isFamilyDoctor(#id)")
  @GetMapping("/{id}")
  public ResponseEntity<PatientDomain> getPatientDetails(@PathVariable Long id) {
    return ResponseEntity.ok(patientService.getPatientById(id));
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @GetMapping("/{id}/admin")
  public ResponseEntity<PatientUserDomain> getPatientUserDetails(@PathVariable Long id) {
    return ResponseEntity.ok(patientService.getPatientUserDetailsById(id));
  }

  @PreAuthorize("hasAuthority('ROLE_PATIENT') and @securityService.isPatientSelf(#id)")
  @PutMapping("/{id}")
  public ResponseEntity<PatientDomain> updatePatient(
      @PathVariable Long id, @RequestBody PatientUpdateRequestDomain patientDomain) {
    return ResponseEntity.ok(patientService.updatePatient(id, patientDomain));
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
    patientService.deletePatient(id);
    return ResponseEntity.noContent().build();
  }
}
