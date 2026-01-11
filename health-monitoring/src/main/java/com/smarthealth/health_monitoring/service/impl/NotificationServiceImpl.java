package com.smarthealth.health_monitoring.service.impl;

import com.smarthealth.health_monitoring.mapper.AlertMapper;
import com.smarthealth.health_monitoring.mapper.NotificationMapper;
import com.smarthealth.health_monitoring.model.domain.AlertDomain;
import com.smarthealth.health_monitoring.model.domain.NotificationDomain;
import com.smarthealth.health_monitoring.model.entity.Alert;
import com.smarthealth.health_monitoring.model.entity.Notification;
import com.smarthealth.health_monitoring.model.enums.NotificationChannelEnum;
import com.smarthealth.health_monitoring.model.enums.NotificationStatusEnum;
import com.smarthealth.health_monitoring.repository.NotificationRepository;
import com.smarthealth.health_monitoring.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final AlertMapper alertMapper;
  private final NotificationMapper notificationMapper;

  public List<NotificationDomain> getMyNotifications() {
    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    if (auth == null || !auth.isAuthenticated()) {
      return null;
    }
    String email = auth.getName();

    List<Notification> notifications = notificationRepository.findAllByAlert_Patient_Email(email);
    return notifications.stream().map(notificationMapper::toDomain).toList();
  }

  public void markAsRead(Long notificationId) {
    Notification notification =
        notificationRepository
            .findById(notificationId)
            .orElseThrow(() -> new AccessDeniedException("Notification not found"));

    notification.setStatus(NotificationStatusEnum.READ);
    notificationRepository.save(notification);
  }

  public void notifyForAlert(AlertDomain alertDomain) {
    Notification notification = new Notification();
    Alert alert = alertMapper.toEntity(alertDomain);
    notification.setAlert(alert);
    notification.setChannel(NotificationChannelEnum.EMAIL);
    notification.setStatus(NotificationStatusEnum.DELIVERED);
    notificationRepository.save(notification);
  }
}
