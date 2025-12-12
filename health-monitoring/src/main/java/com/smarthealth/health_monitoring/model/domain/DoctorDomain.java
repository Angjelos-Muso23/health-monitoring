package com.smarthealth.health_monitoring.model.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DoctorDomain {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String specialization;
    private String licenseNumber;
}