package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.SensorDomain;
import com.smarthealth.health_monitoring.model.entity.Sensor;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface SensorMapper {

    SensorDomain toDomain(Sensor entity);

    Sensor toEntity(SensorDomain domain);
}