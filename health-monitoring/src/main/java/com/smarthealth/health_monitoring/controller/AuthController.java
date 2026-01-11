package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.*;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorRegisterRequestDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientRegisterRequestDomain;
import com.smarthealth.health_monitoring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/register/patient")
  public ResponseEntity<PatientDomain> registerPatient(
      @RequestBody PatientRegisterRequestDomain request) {
    return ResponseEntity.ok(authService.registerPatient(request));
  }

  @PostMapping("/register/doctor")
  public ResponseEntity<DoctorDomain> registerDoctor(
      @RequestBody DoctorRegisterRequestDomain request) {
    return ResponseEntity.ok(authService.registerDoctor(request));
  }

  @PostMapping(value = "/login", produces = "application/json")
  public ResponseEntity<String> login(@RequestBody LoginRequest request) {
    return ResponseEntity.ok(authService.login(request));
  }
}
