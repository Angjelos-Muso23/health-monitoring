package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.*;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorRegisterRequestDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientRegisterRequestDomain;

public interface AuthService {

  String login(LoginRequest request);

  PatientDomain registerPatient(PatientRegisterRequestDomain request);

  DoctorDomain registerDoctor(DoctorRegisterRequestDomain request);
}
