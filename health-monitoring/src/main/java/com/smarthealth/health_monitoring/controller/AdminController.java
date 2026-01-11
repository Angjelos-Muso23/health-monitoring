package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.admin.UsersDomain;
import com.smarthealth.health_monitoring.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

  private final AdminService adminService;

  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  @GetMapping("/users")
  public ResponseEntity<UsersDomain> getAllUsers() {
    return ResponseEntity.ok(adminService.getAllUsers());
  }
}
