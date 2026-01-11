package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.ReportDomain;
import java.util.List;

public interface ReportService {
  ReportDomain generateReport(Long patientId, ReportDomain request);

  List<ReportDomain> getReportsByPatient(Long patientId);
}
