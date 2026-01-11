package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.service.DoctorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doctors")
@RequiredArgsConstructor
public class DoctorController {

  private final DoctorService doctorService;

  @PreAuthorize(
      "(hasAuthority('ROLE_DOCTOR') and @securityService.isDoctorSelf(#id)) or hasAuthority('ROLE_ADMIN')")
  @GetMapping("/{id}")
  public ResponseEntity<DoctorDomain> getDoctor(@PathVariable Long id) {
    return ResponseEntity.ok(doctorService.getDoctorById(id));
  }

  @PreAuthorize(
      "(hasAuthority('ROLE_DOCTOR') and @securityService.isDoctorSelf(#id)) or hasAuthority('ROLE_ADMIN')")
  @GetMapping("/{id}/patients")
  public ResponseEntity<List<PatientDomain>> getDoctorPatients(@PathVariable Long id) {
    return ResponseEntity.ok(doctorService.getPatientsByDoctor(id));
  }

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
    doctorService.deleteDoctor(id);
    return ResponseEntity.noContent().build();
  }
}
