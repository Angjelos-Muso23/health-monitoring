package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.DoctorMapper;
import com.smarthealth.health_monitoring.mapper.PatientMapper;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.DoctorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

  private final DoctorRepository doctorRepository;
  private final DoctorMapper doctorMapper;
  private final PatientRepository patientRepository;
  private final PatientMapper patientMapper;

  public DoctorDomain getDoctorById(Long id) {
    Doctor doctor =
        doctorRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));
    return doctorMapper.toDomain(doctor);
  }

  public List<PatientDomain> getPatientsByDoctor(Long id) {
    if (!doctorRepository.existsById(id)) {
      throw new ResourceNotFoundException("Doctor not found");
    }
    List<Patient> patients = patientRepository.findAllByDoctor_Id(id);
    return patients.stream().map(patientMapper::toDomain).toList();
  }

  public void deleteDoctor(Long id) {
    if (!doctorRepository.existsById(id)) {
      throw new ResourceNotFoundException("Doctor not found");
    }
    doctorRepository.deleteById(id);
  }
}
