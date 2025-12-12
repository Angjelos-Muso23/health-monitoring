package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.DoctorDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface DoctorMapper {

    DoctorDomain toDomain(Doctor entity);

    Doctor toEntity(DoctorDomain domain);
}