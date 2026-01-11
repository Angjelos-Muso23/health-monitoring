package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.MeasurementDomain;
import com.smarthealth.health_monitoring.model.entity.Measurement;
import org.mapstruct.*;

@Mapper(config = MapperConfig.class)
public interface MeasurementMapper {

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @Mapping(target = "patient", ignore = true)
  @Mapping(target = "sensor", ignore = true)
  Measurement toEntity(MeasurementDomain domain);

  @Mapping(target = "patientId", source = "patient.id")
  @Mapping(target = "sensorId", source = "sensor.id")
  MeasurementDomain toDomain(Measurement entity);

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromDomain(MeasurementDomain domain, @MappingTarget Measurement entity);
}
