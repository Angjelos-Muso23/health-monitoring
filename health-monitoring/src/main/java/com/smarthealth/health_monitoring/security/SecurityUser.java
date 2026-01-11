package com.smarthealth.health_monitoring.security;

import com.smarthealth.health_monitoring.common.User;
import com.smarthealth.health_monitoring.model.enums.UserRoleEnum;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@RequiredArgsConstructor
public class SecurityUser implements UserDetails {

  private final Long id;
  private final String email;
  private final String password;
  private final UserRoleEnum role;

  public SecurityUser(User user) {
    this(user.getId(), user.getEmail(), user.getPasswordHash(), user.getRole());
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
