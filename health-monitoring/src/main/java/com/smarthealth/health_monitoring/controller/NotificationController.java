package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.NotificationDomain;
import com.smarthealth.health_monitoring.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationService notificationService;

  @GetMapping
  public ResponseEntity<List<NotificationDomain>> getMyNotifications() {
    return ResponseEntity.ok(notificationService.getMyNotifications());
  }

  @PutMapping("/{id}/read")
  public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
    notificationService.markAsRead(id);
    return ResponseEntity.ok().build();
  }
}
