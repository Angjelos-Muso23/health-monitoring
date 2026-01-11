package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactCreateRequestDomain;
import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactDomain;
import java.util.List;

public interface EmergencyContactService {

  EmergencyContactDomain createEmergencyContact(
      Long patientId, EmergencyContactCreateRequestDomain request);

  List<EmergencyContactDomain> getEmergencyContactsByPatient(Long patientId);

  void deleteEmergencyContact(Long contactId);
}
