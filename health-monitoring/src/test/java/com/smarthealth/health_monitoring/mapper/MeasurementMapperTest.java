package com.smarthealth.health_monitoring.mapper;
import static org.assertj.core.api.Assertions.*;

import com.smarthealth.health_monitoring.model.domain.MeasurementDomain;
import com.smarthealth.health_monitoring.model.entity.Measurement;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.model.entity.Sensor;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class MeasurementMapperTest {

    private final MeasurementMapper mapper =
            Mappers.getMapper(MeasurementMapper.class);

    @Test
    void toDomain_shouldMapPatientAndSensorIds() {
        Patient patient = new Patient();
        patient.setId(5L);

        Sensor sensor = new Sensor();
        sensor.setId(9L);

        Measurement measurement = new Measurement();
        measurement.setPatient(patient);
        measurement.setSensor(sensor);

        MeasurementDomain domain = mapper.toDomain(measurement);

        assertThat(domain.getPatientId()).isEqualTo(5L);
        assertThat(domain.getSensorId()).isEqualTo(9L);
    }

    @Test
    void toEntity_shouldIgnoreRelations() {
        MeasurementDomain domain = MeasurementDomain.builder().value(120F).build();

        Measurement entity = mapper.toEntity(domain);

        assertThat(entity.getPatient()).isNull();
        assertThat(entity.getSensor()).isNull();
    }
}

