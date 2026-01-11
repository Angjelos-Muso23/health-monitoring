package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.AlertDomain;
import com.smarthealth.health_monitoring.model.entity.Alert;
import org.mapstruct.*;

@Mapper(config = MapperConfig.class)
public interface AlertMapper {

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @Mapping(target = "patient", ignore = true)
  Alert toEntity(AlertDomain domain);

  @Mapping(target = "patientId", source = "patient.id")
  AlertDomain toDomain(Alert entity);

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromDomain(AlertDomain domain, @MappingTarget Alert entity);
}
