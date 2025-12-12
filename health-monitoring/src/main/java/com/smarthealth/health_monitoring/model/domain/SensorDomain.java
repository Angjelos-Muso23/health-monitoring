package com.smarthealth.health_monitoring.model.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SensorDomain {

    private String sensorType;
    private String model;
    private Date pairedAt;
    private String status;
}