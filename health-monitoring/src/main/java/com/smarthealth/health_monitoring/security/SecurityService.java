package com.smarthealth.health_monitoring.security;

import com.smarthealth.health_monitoring.repository.*;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("securityService")
@RequiredArgsConstructor
public class SecurityService {

  private final PatientRepository patientRepository;
  private final EmergencyContactRepository emergencyContactRepository;
  private final DoctorRepository doctorRepository;
  private final SensorRepository sensorRepository;
  private final AlertRepository alertRepository;
  private final MedicalHistoryRepository medicalHistoryRepository;

  private String getAuthenticatedEmail() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      return null;
    }
    return auth.getName();
  }


  public boolean isPatientSelf(Long patientId) {
    String email = getAuthenticatedEmail();
    if (email == null) return false;

    return patientRepository.existsByIdAndEmail(patientId, email);
  }

  public boolean isDoctorSelf(Long doctorId) {
    String email = getAuthenticatedEmail();
    if (email == null) return false;

    return doctorRepository.existsByIdAndEmail(doctorId, email);
  }

  public boolean isFamilyDoctor(Long patientId) {
    String email = getAuthenticatedEmail();
    if (email == null) return false;

    return patientRepository.existsByIdAndDoctor_Email(patientId, email);
  }

  public boolean isOwnerOfContact(Long contactId) {
    String email = getAuthenticatedEmail();
    if (email == null) return false;

    return emergencyContactRepository.existsByIdAndPatient_Email(contactId, email);
  }

  public boolean isOwnerOfSensor(Long sensorId) {
    String email = getAuthenticatedEmail();
    if (email == null) return false;

    return sensorRepository.existsByIdAndPatient_Email(sensorId, email);
  }

  public boolean isOwnerOfAlert(Long alertId) {
    String email = getAuthenticatedEmail();
    if (email == null) return false;

    return alertRepository.existsByIdAndPatient_Email(alertId, email);
  }

  public boolean isMedicalHistoryDoctor(Long historyId) {
    String email = getAuthenticatedEmail();
    if (email == null) return false;

    return medicalHistoryRepository.existsByIdAndDoctor_Email(historyId, email);
  }
}
