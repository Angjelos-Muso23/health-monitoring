package com.smarthealth.health_monitoring.mapper;
import static org.assertj.core.api.Assertions.*;

import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientRegisterRequestDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientUpdateRequestDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class PatientMapperTest {

    private final PatientMapper mapper =
            Mappers.getMapper(PatientMapper.class);

    @Test
    void toEntity_shouldIgnorePasswordAndDoctor() {
        PatientRegisterRequestDomain request =
                PatientRegisterRequestDomain.builder()
                        .email("patient@test.com")
                        .firstName("John")
                        .lastName("Doe")
                        .build();

        Patient entity = mapper.toEntity(request);

        assertThat(entity.getEmail()).isEqualTo("patient@test.com");
        assertThat(entity.getPasswordHash()).isNull();
        assertThat(entity.getDoctor()).isNull();
        assertThat(entity.getCreateDate()).isNull();
    }

    @Test
    void toDomain_shouldMapDoctorId() {
        Doctor doctor = new Doctor();
        doctor.setId(99L);

        Patient patient = new Patient();
        patient.setDoctor(doctor);

        PatientDomain domain = mapper.toDomain(patient);

        assertThat(domain.getDoctorId()).isEqualTo(99L);
    }

    @Test
    void updateEntity_shouldIgnoreNullValues() {
        Patient entity = new Patient();
        entity.setFirstName("OldName");

        PatientUpdateRequestDomain update =
                PatientUpdateRequestDomain.builder()
                        .firstName(null)
                        .build();

        mapper.updateEntityFromDomain(update, entity);

        assertThat(entity.getFirstName()).isEqualTo("OldName");
    }
}

