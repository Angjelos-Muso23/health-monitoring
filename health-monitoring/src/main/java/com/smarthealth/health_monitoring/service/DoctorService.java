package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import java.util.List;

public interface DoctorService {
  DoctorDomain getDoctorById(Long id);

  List<PatientDomain> getPatientsByDoctor(Long id);

  void deleteDoctor(Long id);
}
