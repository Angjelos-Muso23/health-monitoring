package com.smarthealth.health_monitoring.security;

import com.smarthealth.health_monitoring.repository.AdminRepository;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final AdminRepository adminRepository;
  private final DoctorRepository doctorRepository;
  private final PatientRepository patientRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

    return adminRepository
        .findByEmail(email)
        .<UserDetails>map(SecurityUser::new)
        .or(() -> doctorRepository.findByEmail(email).map(SecurityUser::new))
        .or(() -> patientRepository.findByEmail(email).map(SecurityUser::new))
        .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
  }
}
