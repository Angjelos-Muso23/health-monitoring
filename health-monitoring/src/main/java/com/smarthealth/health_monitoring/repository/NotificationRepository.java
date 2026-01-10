package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
  List<Notification> findAllByAlert_Patient_Email(String email);
}
