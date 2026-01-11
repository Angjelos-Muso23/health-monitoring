package com.smarthealth.health_monitoring.mapper;
import static org.assertj.core.api.Assertions.*;

import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorRegisterRequestDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class DoctorMapperTest {

    private final DoctorMapper mapper = Mappers.getMapper(DoctorMapper.class);

    @Test
    void toEntity_shouldIgnorePasswordHashAndDates() {
        DoctorRegisterRequestDomain request =
                DoctorRegisterRequestDomain.builder()
                        .email("doc@test.com")
                        .firstName("Andy")
                        .lastName("Specter")
                        .build();

        Doctor doctor = mapper.toEntity(request);

        assertThat(doctor.getEmail()).isEqualTo("doc@test.com");
        assertThat(doctor.getPasswordHash()).isNull();
        assertThat(doctor.getCreateDate()).isNull();
    }

    @Test
    void updateEntity_shouldUpdateFields() {
        Doctor entity = new Doctor();
        entity.setFirstName("Old");

        DoctorDomain update = DoctorDomain.builder()
                .firstName("New")
                .build();

        mapper.updateEntityFromDomain(update, entity);

        assertThat(entity.getFirstName()).isEqualTo("New");
    }
}
