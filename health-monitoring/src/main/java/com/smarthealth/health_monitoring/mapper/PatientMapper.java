package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientRegisterRequestDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientUpdateRequestDomain;
import com.smarthealth.health_monitoring.model.entity.Patient;
import org.mapstruct.*;

@Mapper(config = MapperConfig.class)
public interface PatientMapper {

  @Mapping(target = "passwordHash", ignore = true)
  @Mapping(target = "doctor", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  Patient toEntity(PatientRegisterRequestDomain domain);

  @Mapping(target = "doctorId", source = "doctor.id")
  PatientDomain toDomain(Patient entity);

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void updateEntityFromDomain(PatientUpdateRequestDomain domain, @MappingTarget Patient entity);
}
