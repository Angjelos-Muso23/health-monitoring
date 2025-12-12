package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
}
