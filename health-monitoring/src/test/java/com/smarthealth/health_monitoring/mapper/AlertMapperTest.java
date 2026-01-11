package com.smarthealth.health_monitoring.mapper;
import static org.assertj.core.api.Assertions.*;

import com.smarthealth.health_monitoring.model.domain.AlertDomain;
import com.smarthealth.health_monitoring.model.entity.Alert;
import com.smarthealth.health_monitoring.model.entity.Patient;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class AlertMapperTest {

    private final AlertMapper mapper = Mappers.getMapper(AlertMapper.class);

    @Test
    void toEntity_shouldIgnorePatientAndDates() {
        AlertDomain domain = AlertDomain.builder()
                .message("High heart rate")
                .level("HIGH")
                .build();

        Alert entity = mapper.toEntity(domain);

        assertThat(entity.getMessage()).isEqualTo("High heart rate");
        assertThat(entity.getPatient()).isNull();
        assertThat(entity.getCreateDate()).isNull();
        assertThat(entity.getModifyDate()).isNull();
    }

    @Test
    void toDomain_shouldMapPatientId() {
        Patient patient = new Patient();
        patient.setId(10L);

        Alert alert = new Alert();
        alert.setPatient(patient);

        AlertDomain domain = mapper.toDomain(alert);

        assertThat(domain.getPatientId()).isEqualTo(10L);
    }

    @Test
    void updateEntity_shouldIgnoreNulls() {
        Alert entity = new Alert();
        entity.setMessage("Old");

        AlertDomain update = new AlertDomain();
        update.setMessage(null);

        mapper.updateEntityFromDomain(update, entity);

        assertThat(entity.getMessage()).isEqualTo("Old");
    }
}

