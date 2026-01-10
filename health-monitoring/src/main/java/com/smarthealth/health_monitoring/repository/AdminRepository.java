package com.smarthealth.health_monitoring.repository;

import com.smarthealth.health_monitoring.model.entity.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {
  Optional<Admin> findByEmail(String email);
}
