package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.PatientDomain;
import com.smarthealth.health_monitoring.model.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface PatientMapper {

    @Mapping(source = "doctor.id", target = "doctorId")
    PatientDomain toDomain(Patient entity);

    @Mapping(source = "doctorId", target = "doctor.id")
    Patient toEntity(PatientDomain domain);
}