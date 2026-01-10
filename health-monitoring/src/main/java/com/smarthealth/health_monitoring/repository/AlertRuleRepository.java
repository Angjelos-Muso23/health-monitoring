package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.AlertRule;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertRuleRepository extends JpaRepository<AlertRule, Long> {

  Optional<AlertRule> findByPatient_Id(Long id);
}
