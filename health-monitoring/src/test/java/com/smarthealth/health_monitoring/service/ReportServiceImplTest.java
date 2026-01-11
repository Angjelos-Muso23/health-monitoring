package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.mapper.ReportMapper;
import com.smarthealth.health_monitoring.model.domain.ReportDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.model.entity.Report;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.repository.ReportRepository;
import com.smarthealth.health_monitoring.service.impl.ReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ReportServiceImplTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock private DoctorRepository doctorRepository;
    @Mock private ReportMapper reportMapper;
    @Mock private ReportRepository reportRepository;

    @InjectMocks
    private ReportServiceImpl service;

    @Test
    void generateReport_success() {
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        Report report = new Report();
        ReportDomain domain = new ReportDomain();
        domain.setDoctorId(1L);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(reportMapper.toEntity(domain)).thenReturn(report);
        when(reportRepository.save(report)).thenReturn(report);
        when(reportMapper.toDomain(report)).thenReturn(domain);

        ReportDomain result = service.generateReport(1L, domain);

        assertNotNull(result);
    }

    @Test
    void getReportsByPatient_success() {
        when(patientRepository.existsById(1L)).thenReturn(true);
        when(reportRepository.findAllByPatient_Id(1L))
                .thenReturn(List.of(new Report()));
        when(reportMapper.toDomain(any())).thenReturn(new ReportDomain());

        List<ReportDomain> result = service.getReportsByPatient(1L);

        assertEquals(1, result.size());
    }
}

