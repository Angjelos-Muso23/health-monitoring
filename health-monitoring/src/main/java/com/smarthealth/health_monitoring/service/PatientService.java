package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientUpdateRequestDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientUserDomain;

public interface PatientService {

  PatientDomain getPatientById(Long id);

  PatientUserDomain getPatientUserDetailsById(Long id);

  PatientDomain updatePatient(Long id, PatientUpdateRequestDomain patientDomain);

  void deletePatient(Long id);
}
