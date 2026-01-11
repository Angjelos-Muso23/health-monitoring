package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.ReportDomain;
import com.smarthealth.health_monitoring.model.entity.Report;
import org.mapstruct.*;

@Mapper(config = MapperConfig.class)
public interface ReportMapper {

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @Mapping(target = "doctor", ignore = true)
  @Mapping(target = "patient", ignore = true)
  Report toEntity(ReportDomain domain);

  @Mapping(target = "doctorId", source = "doctor.id")
  @Mapping(target = "patientId", source = "patient.id")
  ReportDomain toDomain(Report entity);

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromDomain(ReportDomain domain, @MappingTarget Report entity);
}
