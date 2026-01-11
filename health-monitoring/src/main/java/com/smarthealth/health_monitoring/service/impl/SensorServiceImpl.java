package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.SensorMapper;
import com.smarthealth.health_monitoring.model.domain.SensorDomain;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.model.entity.Sensor;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.repository.SensorRepository;
import com.smarthealth.health_monitoring.service.SensorService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SensorServiceImpl implements SensorService {

  private final PatientRepository patientRepository;
  private final SensorMapper sensorMapper;
  private final SensorRepository sensorRepository;

  public SensorDomain addSensor(Long patientId, SensorDomain request) {
    Patient patient =
        patientRepository
            .findById(patientId)
            .orElseThrow(() -> new ResourceNotFoundException("Patient not found"));
    Sensor sensor = sensorMapper.toEntity(request);
    sensor.setPatient(patient);
    Sensor savedSensor = sensorRepository.save(sensor);
    return sensorMapper.toDomain(savedSensor);
  }

  public List<SensorDomain> getSensorsByPatient(Long patientId) {
    if (!patientRepository.existsById(patientId)) {
      throw new ResourceNotFoundException("Patient not found");
    }
    List<Sensor> sensors = sensorRepository.findAllByPatient_Id(patientId);
    return sensors.stream().map(sensorMapper::toDomain).toList();
  }

  public void deleteSensor(Long sensorId) {
    Sensor existing =
        sensorRepository
            .findById(sensorId)
            .orElseThrow(() -> new ResourceNotFoundException("Sensor not found"));
    sensorRepository.delete(existing);
  }
}
