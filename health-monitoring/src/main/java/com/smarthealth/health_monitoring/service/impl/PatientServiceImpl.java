package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.mapper.*;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientUpdateRequestDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientUserDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.*;
import com.smarthealth.health_monitoring.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

  private final PatientRepository patientRepository;
  private final DoctorRepository doctorRepository;
  private final PatientMapper patientMapper;

  @Override
  public PatientDomain getPatientById(Long id) {
    Patient patient =
        patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
    return patientMapper.toDomain(patient);
  }

  @Override
  public PatientUserDomain getPatientUserDetailsById(Long id) {
    Patient patient =
        patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
    return PatientUserDomain.builder()
        .email(patient.getEmail())
        .firstName(patient.getFirstName())
        .lastName(patient.getLastName())
        .phoneNumber(patient.getPhoneNumber())
        .birthDate(patient.getBirthDate())
        .gender(patient.getGender().toString())
        .build();
  }

  @Override
  public PatientDomain updatePatient(Long id, PatientUpdateRequestDomain request) {
    Patient existing =
        patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));

    patientMapper.updateEntityFromDomain(request, existing);

    if (request.getDoctorEmail() != null) {
      Doctor doctor =
          doctorRepository
              .findByEmail(request.getDoctorEmail())
              .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));

      existing.setDoctor(doctor);
    }
    return patientMapper.toDomain(patientRepository.save(existing));
  }

  @Override
  public void deletePatient(Long id) {
    Patient existing =
        patientRepository.findById(id).orElseThrow(() -> new RuntimeException("Patient not found"));
    patientRepository.delete(existing);
  }
}
