package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.MeasurementMapper;
import com.smarthealth.health_monitoring.model.domain.MeasurementDomain;
import com.smarthealth.health_monitoring.model.entity.Measurement;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.model.entity.Sensor;
import com.smarthealth.health_monitoring.repository.MeasurementRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.repository.SensorRepository;
import com.smarthealth.health_monitoring.service.impl.MeasurementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MeasurementServiceImplTest {

    @Mock private MeasurementRepository measurementRepository;
    @Mock private PatientRepository patientRepository;
    @Mock private SensorRepository sensorRepository;
    @Mock private AlertService alertService;
    @Mock private MeasurementMapper measurementMapper;

    @InjectMocks private MeasurementServiceImpl service;

    @Test
    void createMeasurement_valid_savesAndEvaluates() {
        Patient patient = new Patient(); patient.setId(1L);
        Sensor sensor = new Sensor(); sensor.setPatient(patient);
        Measurement measurement = new Measurement();
        MeasurementDomain domain = new MeasurementDomain();
        domain.setPatientId(1L);
        domain.setSensorId(1L);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(sensorRepository.findById(1L)).thenReturn(Optional.of(sensor));
        when(measurementMapper.toEntity(domain)).thenReturn(measurement);
        when(measurementRepository.save(any())).thenReturn(measurement);
        when(measurementMapper.toDomain(measurement)).thenReturn(domain);

        MeasurementDomain result = service.createMeasurement(domain);

        verify(alertService).evaluate(measurement);
        assertNotNull(result);
    }

    @Test
    void getMeasurementsByPatient_notFound_throws() {
        when(patientRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.getMeasurementsByPatient(1L));
    }

    @Test
    void getLatestMeasurement_notFound_throws() {
        when(patientRepository.existsById(1L)).thenReturn(true);
        when(measurementRepository.findTopByPatient_IdOrderByCreateDateDesc(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getLatestMeasurement(1L));
    }
}

