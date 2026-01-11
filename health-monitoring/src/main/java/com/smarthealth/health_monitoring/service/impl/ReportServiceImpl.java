package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.ReportMapper;
import com.smarthealth.health_monitoring.model.domain.ReportDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.model.entity.Report;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.repository.ReportRepository;
import com.smarthealth.health_monitoring.service.ReportService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final PatientRepository patientRepository;
  private final DoctorRepository doctorRepository;
  private final ReportMapper reportMapper;
  private final ReportRepository reportRepository;

  public ReportDomain generateReport(Long patientId, ReportDomain request) {
    Patient patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    Doctor doctor =
        doctorRepository
            .findById(request.getDoctorId())
            .orElseThrow(() -> new ResourceNotFoundException("Doctor not found"));

    Report report = reportMapper.toEntity(request);
    report.setPatient(patient);
    report.setDoctor(doctor);
    Report savedReport = reportRepository.save(report);
    return reportMapper.toDomain(savedReport);
  }

  public List<ReportDomain> getReportsByPatient(Long patientId) {
    if (!patientRepository.existsById(patientId)) {
      throw new ResourceNotFoundException("Patient not found");
    }
    List<Report> reports = reportRepository.findAllByPatient_Id(patientId);
    return reports.stream().map(reportMapper::toDomain).toList();
  }
}
