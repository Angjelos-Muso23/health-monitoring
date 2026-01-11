package com.smarthealth.health_monitoring.mapper;
import static org.assertj.core.api.Assertions.*;

import com.smarthealth.health_monitoring.model.domain.PrescriptionDomain;
import com.smarthealth.health_monitoring.model.entity.MedicalHistory;
import com.smarthealth.health_monitoring.model.entity.Prescription;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PrescriptionMapperTest {

    private final PrescriptionMapper mapper =
            Mappers.getMapper(PrescriptionMapper.class);

    @Test
    void toEntity_shouldIgnoreMedicalHistoryAndDates() {
        PrescriptionDomain domain = PrescriptionDomain.builder()
                .medicationName("Aspirin")
                .dosage("1 tablet")
                .build();

        Prescription entity = mapper.toEntity(domain);

        assertThat(entity.getMedicationName()).isEqualTo("Aspirin");
        assertThat(entity.getMedicalHistory()).isNull();
        assertThat(entity.getCreateDate()).isNull();
        assertThat(entity.getModifyDate()).isNull();
    }

    @Test
    void toDomain_shouldMapHistoryId() {
        MedicalHistory history = new MedicalHistory();
        history.setId(42L);

        Prescription entity = new Prescription();
        entity.setMedicalHistory(history);

        PrescriptionDomain domain = mapper.toDomain(entity);

        assertThat(domain.getHistoryId()).isEqualTo(42L);
    }

    @Test
    void updateEntity_shouldIgnoreNullValues() {
        Prescription entity = new Prescription();
        entity.setMedicationName("OldName");

        PrescriptionDomain update = PrescriptionDomain.builder()
                .medicationName(null)
                .build();

        mapper.updateEntityFromDomain(update, entity);

        assertThat(entity.getMedicationName()).isEqualTo("OldName");
    }
}

