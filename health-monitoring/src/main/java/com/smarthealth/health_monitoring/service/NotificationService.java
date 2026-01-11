package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.model.domain.AlertDomain;
import com.smarthealth.health_monitoring.model.domain.NotificationDomain;
import java.util.List;
import org.jspecify.annotations.Nullable;

public interface NotificationService {

  @Nullable List<NotificationDomain> getMyNotifications();

  void markAsRead(Long id);

  void notifyForAlert(AlertDomain alertDomain);
}
