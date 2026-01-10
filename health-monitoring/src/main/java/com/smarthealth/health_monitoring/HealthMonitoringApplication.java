package com.smarthealth.health_monitoring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class HealthMonitoringApplication {

  public static void main(String[] args) {
    SpringApplication.run(HealthMonitoringApplication.class, args);
  }
}
