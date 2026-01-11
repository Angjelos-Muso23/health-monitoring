package com.smarthealth.health_monitoring.mapper;
import static org.assertj.core.api.Assertions.*;

import com.smarthealth.health_monitoring.model.domain.MedicalHistoryDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.MedicalHistory;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class MedicalHistoryMapperTest {

    private final MedicalHistoryMapper mapper =
            Mappers.getMapper(MedicalHistoryMapper.class);

    @Test
    void toDomain_shouldMapDoctorId() {
        Doctor doctor = new Doctor();
        doctor.setId(3L);

        MedicalHistory history = new MedicalHistory();
        history.setDoctor(doctor);

        MedicalHistoryDomain domain = mapper.toDomain(history);

        assertThat(domain.getDoctorId()).isEqualTo(3L);
    }

    @Test
    void updateEntity_shouldIgnoreNulls() {
        MedicalHistory entity = new MedicalHistory();
        entity.setDiagnosis("Old");

        MedicalHistoryDomain update = new MedicalHistoryDomain();
        update.setDiagnosis(null);

        mapper.updateEntityFromDomain(update, entity);

        assertThat(entity.getDiagnosis()).isEqualTo("Old");
    }
}

