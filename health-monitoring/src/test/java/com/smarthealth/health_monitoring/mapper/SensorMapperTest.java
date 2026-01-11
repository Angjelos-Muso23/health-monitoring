package com.smarthealth.health_monitoring.mapper;
import static org.assertj.core.api.Assertions.*;

import com.smarthealth.health_monitoring.model.domain.SensorDomain;
import com.smarthealth.health_monitoring.model.entity.Sensor;
import com.smarthealth.health_monitoring.model.enums.SensorTypeEnum;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class SensorMapperTest {

    private final SensorMapper mapper =
            Mappers.getMapper(SensorMapper.class);

    @Test
    void toEntity_shouldIgnorePatientAndDates() {
        SensorDomain domain = SensorDomain.builder()
                .sensorType("BLOOD_PRESSURE_SENSOR")
                .model("SN-123")
                .build();

        Sensor entity = mapper.toEntity(domain);

        assertThat(entity.getSensorType()).isEqualTo(SensorTypeEnum.BLOOD_PRESSURE_SENSOR);
        assertThat(entity.getModel()).isEqualTo("SN-123");
        assertThat(entity.getPatient()).isNull();
        assertThat(entity.getCreateDate()).isNull();
        assertThat(entity.getModifyDate()).isNull();
    }

    @Test
    void toDomain_shouldMapFields() {
        Sensor entity = new Sensor();
        entity.setSensorType(SensorTypeEnum.BLOOD_PRESSURE_SENSOR);
        entity.setModel("SN-999");

        SensorDomain domain = mapper.toDomain(entity);

        assertThat(domain.getSensorType()).isEqualTo("BLOOD_PRESSURE_SENSOR");
        assertThat(domain.getModel()).isEqualTo("SN-999");
    }
}

