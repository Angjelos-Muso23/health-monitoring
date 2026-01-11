package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactCreateRequestDomain;
import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactDomain;
import com.smarthealth.health_monitoring.model.entity.EmergencyContact;
import org.mapstruct.*;

@Mapper(config = MapperConfig.class)
public interface EmergencyContactMapper {

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  EmergencyContact toEntity(EmergencyContactCreateRequestDomain domain);

  EmergencyContactDomain toDomain(EmergencyContact entity);

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromDomain(
      EmergencyContactDomain domain, @MappingTarget EmergencyContact entity);
}
