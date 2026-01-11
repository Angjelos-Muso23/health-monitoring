package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.PrescriptionDomain;
import com.smarthealth.health_monitoring.model.entity.Prescription;
import org.mapstruct.*;

@Mapper(config = MapperConfig.class)
public interface PrescriptionMapper {

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @Mapping(target = "medicalHistory", ignore = true)
  Prescription toEntity(PrescriptionDomain domain);

  @Mapping(target = "historyId", source = "medicalHistory.id")
  PrescriptionDomain toDomain(Prescription entity);

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromDomain(PrescriptionDomain domain, @MappingTarget Prescription entity);
}
