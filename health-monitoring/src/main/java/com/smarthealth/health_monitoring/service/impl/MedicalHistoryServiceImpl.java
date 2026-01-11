package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.MedicalHistoryMapper;
import com.smarthealth.health_monitoring.model.domain.MedicalHistoryDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.MedicalHistory;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.MedicalHistoryRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.MedicalHistoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicalHistoryServiceImpl implements MedicalHistoryService {

  private final PatientRepository patientRepository;
  private final DoctorRepository doctorRepository;
  private final MedicalHistoryMapper medicalHistoryMapper;
  private final MedicalHistoryRepository medicalHistoryRepository;

  public MedicalHistoryDomain createMedicalHistory(Long patientId, MedicalHistoryDomain request) {
    Patient patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    Doctor doctor =
        doctorRepository
            .findById(request.getDoctorId())
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

    MedicalHistory medicalHistory = medicalHistoryMapper.toEntity(request);
    medicalHistory.setPatient(patient);
    medicalHistory.setDoctor(doctor);
    MedicalHistory savedMedicalHistory = medicalHistoryRepository.save(medicalHistory);

    return medicalHistoryMapper.toDomain(savedMedicalHistory);
  }

  public List<MedicalHistoryDomain> getMedicalHistoryByPatient(Long patientId) {
    if (!patientRepository.existsById(patientId)) {
      throw new ResourceNotFoundException("Patient not found");
    }
    List<MedicalHistory> medicalHistories = medicalHistoryRepository.findAllByPatient_Id(patientId);
    return medicalHistories.stream().map(medicalHistoryMapper::toDomain).toList();
  }
}
