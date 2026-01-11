package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorRegisterRequestDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface DoctorMapper {

  @Mapping(target = "passwordHash", ignore = true)
  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  Doctor toEntity(DoctorRegisterRequestDomain domain);

  DoctorDomain toDomain(Doctor entity);

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  void updateEntityFromDomain(DoctorDomain domain, @MappingTarget Doctor entity);
}
