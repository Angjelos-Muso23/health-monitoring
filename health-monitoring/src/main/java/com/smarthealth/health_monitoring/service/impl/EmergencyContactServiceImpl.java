package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.EmergencyContactMapper;
import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactCreateRequestDomain;
import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactDomain;
import com.smarthealth.health_monitoring.model.entity.EmergencyContact;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.EmergencyContactRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.EmergencyContactService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmergencyContactServiceImpl implements EmergencyContactService {

  private final PatientRepository patientRepository;
  private final EmergencyContactRepository emergencyContactRepository;
  private final EmergencyContactMapper emergencyContactMapper;

  @Override
  public EmergencyContactDomain createEmergencyContact(
      Long patientId, EmergencyContactCreateRequestDomain request) {
    Patient patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

    EmergencyContact emergencyContact = emergencyContactMapper.toEntity(request);
    emergencyContact.setPatient(patient);
    EmergencyContact savedContact = emergencyContactRepository.save(emergencyContact);
    return emergencyContactMapper.toDomain(savedContact);
  }

  @Override
  public List<EmergencyContactDomain> getEmergencyContactsByPatient(Long patientId) {
    if (!patientRepository.existsById(patientId)) {
      throw new ResourceNotFoundException("Patient not found");
    }
    return emergencyContactRepository.findAllByPatient_Id(patientId);
  }

  @Override
  public void deleteEmergencyContact(Long contactId) {
    EmergencyContact existing =
        emergencyContactRepository
            .findById(contactId)
            .orElseThrow(() -> new RuntimeException("Emergency Contact not found"));
    emergencyContactRepository.delete(existing);
  }
}
