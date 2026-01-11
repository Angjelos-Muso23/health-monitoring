package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.mapper.DoctorMapper;
import com.smarthealth.health_monitoring.mapper.PatientMapper;
import com.smarthealth.health_monitoring.model.domain.admin.UsersDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.AdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

  private final PatientRepository patientRepository;
  private final DoctorRepository doctorRepository;
  private final PatientMapper patientMapper;
  private final DoctorMapper doctorMapper;

  public UsersDomain getAllUsers() {
    List<Patient> patients = patientRepository.findAll();
    List<Doctor> doctors = doctorRepository.findAll();
    return UsersDomain.builder()
        .patients(patients.stream().map(patientMapper::toDomain).toList())
        .doctors(doctors.stream().map(doctorMapper::toDomain).toList())
        .build();
  }
}
