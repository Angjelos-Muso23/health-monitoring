package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.AlertRuleDomain;
import com.smarthealth.health_monitoring.model.entity.AlertRule;
import org.mapstruct.*;

@Mapper(config = MapperConfig.class)
public interface AlertRuleMapper {

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @Mapping(target = "doctor", ignore = true)
  @Mapping(target = "patient", ignore = true)
  AlertRule toEntity(AlertRuleDomain domain);

  @Mapping(target = "doctorId", source = "doctor.id")
  @Mapping(target = "patientId", source = "patient.id")
  AlertRuleDomain toDomain(AlertRule entity);

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromDomain(AlertRuleDomain domain, @MappingTarget AlertRule entity);
}
