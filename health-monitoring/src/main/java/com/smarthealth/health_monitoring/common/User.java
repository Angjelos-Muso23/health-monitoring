package com.smarthealth.health_monitoring.common;

import com.smarthealth.health_monitoring.model.enums.GenderEnum;
import com.smarthealth.health_monitoring.model.enums.UserRoleEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public abstract class User extends AuditedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password_hash", nullable = false)
  private String passwordHash;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name", nullable = false)
  private String lastName;

  @Column(name = "phone_number", nullable = false)
  private String phoneNumber;

  @Column(name = "gender", nullable = false)
  private GenderEnum gender;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false, updatable = false)
  private UserRoleEnum role;
}
