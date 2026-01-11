package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.PrescriptionMapper;
import com.smarthealth.health_monitoring.model.domain.PrescriptionDomain;
import com.smarthealth.health_monitoring.model.entity.MedicalHistory;
import com.smarthealth.health_monitoring.model.entity.Prescription;
import com.smarthealth.health_monitoring.repository.MedicalHistoryRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.repository.PrescriptionRepository;
import com.smarthealth.health_monitoring.service.PrescriptionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrescriptionServiceImpl implements PrescriptionService {

  private final MedicalHistoryRepository medicalHistoryRepository;
  private final PrescriptionMapper prescriptionMapper;
  private final PrescriptionRepository prescriptionRepository;
  private final PatientRepository patientRepository;

  public PrescriptionDomain createPrescription(Long historyId, PrescriptionDomain request) {
    MedicalHistory medicalHistory =
        medicalHistoryRepository
            .findById(historyId)
            .orElseThrow(() -> new ResourceNotFoundException("Medical history not found"));
    Prescription prescription = prescriptionMapper.toEntity(request);
    prescription.setMedicalHistory(medicalHistory);
    Prescription savedPrescription = prescriptionRepository.save(prescription);
    return prescriptionMapper.toDomain(savedPrescription);
  }

  public List<PrescriptionDomain> getPrescriptionsByPatient(Long patientId) {
    if (!patientRepository.existsById(patientId)) {
      throw new ResourceNotFoundException("Patient not found");
    }
    List<Prescription> prescriptions =
        prescriptionRepository.findAllByMedicalHistory_Patient_Id(patientId);
    return prescriptions.stream().map(prescriptionMapper::toDomain).toList();
  }
}
