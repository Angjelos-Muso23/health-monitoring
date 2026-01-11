package com.smarthealth.health_monitoring.mapper;
import static org.assertj.core.api.Assertions.*;

import com.smarthealth.health_monitoring.model.domain.AlertRuleDomain;
import com.smarthealth.health_monitoring.model.entity.AlertRule;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class AlertRuleMapperTest {

    private final AlertRuleMapper mapper = Mappers.getMapper(AlertRuleMapper.class);

    @Test
    void toDomain_shouldMapDoctorAndPatientIds() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);

        Patient patient = new Patient();
        patient.setId(2L);

        AlertRule rule = new AlertRule();
        rule.setDoctor(doctor);
        rule.setPatient(patient);

        AlertRuleDomain domain = mapper.toDomain(rule);

        assertThat(domain.getDoctorId()).isEqualTo(1L);
        assertThat(domain.getPatientId()).isEqualTo(2L);
    }

    @Test
    void updateEntity_shouldIgnoreNullFields() {
        AlertRule entity = new AlertRule();
        entity.setMaxValue(100F);

        AlertRuleDomain update = new AlertRuleDomain();
        update.setMaxValue(200F);

        mapper.updateEntityFromDomain(update, entity);

        assertThat(entity.getMaxValue()).isEqualTo(200F);
    }
}

