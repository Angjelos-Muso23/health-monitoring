package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.exception.custom.ResourceAlreadyExistsException;
import com.smarthealth.health_monitoring.mapper.DoctorMapper;
import com.smarthealth.health_monitoring.mapper.PatientMapper;
import com.smarthealth.health_monitoring.model.domain.LoginRequest;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorRegisterRequestDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientRegisterRequestDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.model.enums.UserRoleEnum;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.security.JwtUtil;
import com.smarthealth.health_monitoring.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthenticationManager authenticationManager;
  private final UserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;
  private final PasswordEncoder passwordEncoder;
  private final PatientRepository patientRepository;
  private final PatientMapper patientMapper;
  private final DoctorRepository doctorRepository;
  private final DoctorMapper doctorMapper;

  @Override
  public PatientDomain registerPatient(PatientRegisterRequestDomain request) {
    if (request.getEmail() == null
        || request.getPassword() == null
        || request.getFirstName() == null
        || request.getLastName() == null
        || request.getPhoneNumber() == null
        || request.getBirthDate() == null
        || request.getGender() == null
        || request.getDoctorEmail() == null) {
      throw new IllegalArgumentException("Credentials must not be null or empty.");
    }

    if (patientRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new ResourceAlreadyExistsException("Patient already exists.");
    }

    Patient newPatient = patientMapper.toEntity(request);
    newPatient.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    newPatient.setDoctor(
        doctorRepository
            .findByEmail(request.getDoctorEmail())
            .orElseThrow(
                () ->
                    new IllegalArgumentException(
                        "Doctor with email " + request.getDoctorEmail() + " not found.")));
    newPatient.setRole(UserRoleEnum.PATIENT);
    Patient savedPatient = patientRepository.save(newPatient);
    return patientMapper.toDomain(savedPatient);
  }

  @Override
  public DoctorDomain registerDoctor(DoctorRegisterRequestDomain request) {
    if (request.getEmail() == null
        || request.getPassword() == null
        || request.getFirstName() == null
        || request.getLastName() == null
        || request.getPhoneNumber() == null
        || request.getBirthDate() == null
        || request.getGender() == null
        || request.getSpecialization() == null
        || request.getLicenseNumber() == null) {
      throw new IllegalArgumentException("Credentials must not be null or empty.");
    }

    if (doctorRepository.findByEmail(request.getEmail()).isPresent()) {
      throw new ResourceAlreadyExistsException("Doctor already exists.");
    }

    Doctor newDoctor = doctorMapper.toEntity(request);
    newDoctor.setPasswordHash(passwordEncoder.encode(request.getPassword()));
    newDoctor.setRole(UserRoleEnum.DOCTOR);
    Doctor savedDoctor = doctorRepository.save(newDoctor);

    return doctorMapper.toDomain(savedDoctor);
  }

  @Override
  public String login(LoginRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
    return jwtUtil.generateToken(userDetails);
  }
}
