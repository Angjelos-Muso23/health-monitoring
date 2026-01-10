package com.smarthealth.health_monitoring.model.domain.admin;

import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsersDomain {

  List<DoctorDomain> doctors;
  List<PatientDomain> patients;
}
