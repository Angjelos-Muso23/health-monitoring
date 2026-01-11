package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.SensorDomain;
import com.smarthealth.health_monitoring.service.SensorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sensors")
@RequiredArgsConstructor
public class SensorController {

  private final SensorService sensorService;

  @PreAuthorize("hasAuthority('ROLE_PATIENT') and @securityService.isPatientSelf(#patientId)")
  @PostMapping("/patients/{patientId}")
  public ResponseEntity<SensorDomain> addSensor(
      @PathVariable Long patientId, @RequestBody SensorDomain request) {
    return ResponseEntity.ok(sensorService.addSensor(patientId, request));
  }

  @PreAuthorize(
      "(hasAuthority('ROLE_DOCTOR') and @securityService.isFamilyDoctor(#patientId)) or (hasAuthority('ROLE_PATIENT') and @securityService.isPatientSelf(#patientId))")
  @GetMapping("/patients/{patientId}")
  public ResponseEntity<List<SensorDomain>> getPatientSensors(@PathVariable Long patientId) {
    return ResponseEntity.ok(sensorService.getSensorsByPatient(patientId));
  }

  @PreAuthorize("hasAuthority('ROLE_PATIENT') and @securityService.isOwnerOfSensor(#sensorId)")
  @DeleteMapping("/{sensorId}")
  public ResponseEntity<Void> deleteSensor(@PathVariable Long sensorId) {
    sensorService.deleteSensor(sensorId);
    return ResponseEntity.noContent().build();
  }
}
