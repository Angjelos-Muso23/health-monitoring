package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.mapper.SensorMapper;
import com.smarthealth.health_monitoring.model.domain.SensorDomain;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.model.entity.Sensor;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.repository.SensorRepository;
import com.smarthealth.health_monitoring.service.impl.SensorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SensorServiceImplTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock private SensorMapper sensorMapper;
    @Mock private SensorRepository sensorRepository;

    @InjectMocks
    private SensorServiceImpl service;

    @Test
    void addSensor_success() {
        Patient patient = new Patient();
        Sensor sensor = new Sensor();
        SensorDomain domain = new SensorDomain();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(sensorMapper.toEntity(domain)).thenReturn(sensor);
        when(sensorRepository.save(sensor)).thenReturn(sensor);
        when(sensorMapper.toDomain(sensor)).thenReturn(domain);

        SensorDomain result = service.addSensor(1L, domain);

        assertNotNull(result);
    }

    @Test
    void getSensorsByPatient_success() {
        when(patientRepository.existsById(1L)).thenReturn(true);
        when(sensorRepository.findAllByPatient_Id(1L))
                .thenReturn(List.of(new Sensor()));
        when(sensorMapper.toDomain(any())).thenReturn(new SensorDomain());

        List<SensorDomain> result = service.getSensorsByPatient(1L);

        assertEquals(1, result.size());
    }

    @Test
    void deleteSensor_success() {
        Sensor sensor = new Sensor();
        when(sensorRepository.findById(1L)).thenReturn(Optional.of(sensor));

        service.deleteSensor(1L);

        verify(sensorRepository).delete(sensor);
    }
}

