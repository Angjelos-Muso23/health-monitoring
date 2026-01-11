package com.smarthealth.health_monitoring.mapper;
import static org.assertj.core.api.Assertions.*;

import com.smarthealth.health_monitoring.mapper.ReportMapper;
import com.smarthealth.health_monitoring.model.domain.ReportDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.model.entity.Report;
import com.smarthealth.health_monitoring.model.enums.ReportTypeEnum;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class ReportMapperTest {

    private final ReportMapper mapper =
            Mappers.getMapper(ReportMapper.class);

    @Test
    void toEntity_shouldIgnoreDoctorPatientAndDates() {
        ReportDomain domain = ReportDomain.builder()
                .reportType(ReportTypeEnum.DAILY)
                .build();

        Report entity = mapper.toEntity(domain);

        assertThat(entity.getReportType()).isEqualTo(ReportTypeEnum.DAILY);
        assertThat(entity.getDoctor()).isNull();
        assertThat(entity.getPatient()).isNull();
    }

    @Test
    void toDomain_shouldMapDoctorAndPatientIds() {
        Doctor doctor = new Doctor();
        doctor.setId(10L);

        Patient patient = new Patient();
        patient.setId(20L);

        Report report = new Report();
        report.setDoctor(doctor);
        report.setPatient(patient);

        ReportDomain domain = mapper.toDomain(report);

        assertThat(domain.getDoctorId()).isEqualTo(10L);
        assertThat(domain.getPatientId()).isEqualTo(20L);
    }

    @Test
    void updateEntity_shouldIgnoreNullValues() {
        Report entity = new Report();
        entity.setReportType(ReportTypeEnum.DAILY);

        ReportDomain update = ReportDomain.builder()
                .reportType(null)
                .build();

        mapper.updateEntityFromDomain(update, entity);

        assertThat(entity.getReportType()).isEqualTo(ReportTypeEnum.DAILY);
    }
}

