package com.smarthealth.health_monitoring.mapper;
import static org.assertj.core.api.Assertions.*;

import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactCreateRequestDomain;
import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactDomain;
import com.smarthealth.health_monitoring.model.entity.EmergencyContact;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class EmergencyContactMapperTest {

    private final EmergencyContactMapper mapper =
            Mappers.getMapper(EmergencyContactMapper.class);

    @Test
    void toEntity_shouldIgnoreDates() {
        EmergencyContactCreateRequestDomain request =
                EmergencyContactCreateRequestDomain.builder()
                        .name("Jane")
                        .phoneNumber("+355123")
                        .build();

        EmergencyContact entity = mapper.toEntity(request);

        assertThat(entity.getName()).isEqualTo("Jane");
        assertThat(entity.getCreateDate()).isNull();
    }

    @Test
    void updateEntity_shouldIgnoreNulls() {
        EmergencyContact entity = new EmergencyContact();
        entity.setName("Old");

        EmergencyContactDomain update = new EmergencyContactDomain();
        update.setName(null);

        mapper.updateEntityFromDomain(update, entity);

        assertThat(entity.getName()).isEqualTo("Old");
    }
}

