package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.MeasurementMapper;
import com.smarthealth.health_monitoring.model.domain.MeasurementDomain;
import com.smarthealth.health_monitoring.model.entity.Measurement;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.model.entity.Sensor;
import com.smarthealth.health_monitoring.repository.MeasurementRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.repository.SensorRepository;
import com.smarthealth.health_monitoring.service.AlertService;
import com.smarthealth.health_monitoring.service.MeasurementService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeasurementServiceImpl implements MeasurementService {

  private final MeasurementRepository measurementRepository;
  private final PatientRepository patientRepository;
  private final SensorRepository sensorRepository;
  private final AlertService alertService;
  private final MeasurementMapper measurementMapper;

  public MeasurementDomain createMeasurement(MeasurementDomain request) {

    Patient patient =
        patientRepository
            .findById(request.getPatientId())
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));

    Sensor sensor =
        sensorRepository
            .findById(request.getSensorId())
            .orElseThrow(() -> new ResourceNotFoundException("Sensor not found"));

    if (!sensor.getPatient().getId().equals(patient.getId())) {
      throw new IllegalStateException("Sensor does not belong to patient");
    }

    Measurement measurement = measurementMapper.toEntity(request);
    measurement.setPatient(patient);
    measurement.setSensor(sensor);

    Measurement saved = measurementRepository.save(measurement);

    alertService.evaluate(saved);

    return measurementMapper.toDomain(saved);
  }

  public List<MeasurementDomain> getMeasurementsByPatient(Long patientId) {
    if (!patientRepository.existsById(patientId)) {
      throw new ResourceNotFoundException("Patient not found");
    }
    List<Measurement> measurements = measurementRepository.findAllByPatient_Id(patientId);
    return measurements.stream().map(measurementMapper::toDomain).toList();
  }

  public MeasurementDomain getLatestMeasurement(Long patientId) {
    if (!patientRepository.existsById(patientId)) {
      throw new ResourceNotFoundException("Patient not found");
    }
    Measurement latestMeasurement =
        measurementRepository
            .findTopByPatient_IdOrderByCreateDateDesc(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("No measurements found for patient"));
    return measurementMapper.toDomain(latestMeasurement);
  }
}
