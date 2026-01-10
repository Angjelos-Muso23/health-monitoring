package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.MedicalHistoryDomain;
import com.smarthealth.health_monitoring.model.entity.MedicalHistory;
import org.mapstruct.*;

@Mapper(config = MapperConfig.class)
public interface MedicalHistoryMapper {

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @Mapping(target = "doctor", ignore = true)
  @Mapping(target = "patient", ignore = true)
  MedicalHistory toEntity(MedicalHistoryDomain domain);

  @Mapping(target = "doctorId", source = "doctor.id")
  MedicalHistoryDomain toDomain(MedicalHistory entity);

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromDomain(MedicalHistoryDomain domain, @MappingTarget MedicalHistory entity);
}
