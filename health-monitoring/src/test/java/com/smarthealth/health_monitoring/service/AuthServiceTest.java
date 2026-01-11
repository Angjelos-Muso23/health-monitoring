package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.exception.custom.ResourceAlreadyExistsException;
import com.smarthealth.health_monitoring.mapper.DoctorMapper;
import com.smarthealth.health_monitoring.mapper.PatientMapper;
import com.smarthealth.health_monitoring.model.domain.LoginRequest;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorRegisterRequestDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientRegisterRequestDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.model.enums.BloodTypeEnum;
import com.smarthealth.health_monitoring.model.enums.GenderEnum;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.security.JwtUtil;
import com.smarthealth.health_monitoring.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    AuthenticationManager authenticationManager;
    @Mock
    UserDetailsService userDetailsService;
    @Mock
    JwtUtil jwtUtil;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock PatientRepository patientRepository;
    @Mock
    DoctorRepository doctorRepository;
    @Mock
    PatientMapper patientMapper;
    @Mock
    DoctorMapper doctorMapper;

    @InjectMocks
    AuthServiceImpl service;

    private PatientRegisterRequestDomain validPatientRequest() {
        PatientRegisterRequestDomain request = new PatientRegisterRequestDomain();

        request.setEmail("patient1@example.com");
        request.setPassword("StrongPassword123!");
        request.setFirstName("John1");
        request.setLastName("Doe1");
        request.setPhoneNumber("+355691234567");
        request.setBirthDate(LocalDate.of(1995, 6, 15));
        request.setGender(GenderEnum.MALE.toString());

        request.setHeightCm(175);
        request.setWeightKg(72);
        request.setBloodType(BloodTypeEnum.O_POSITIVE.toString());

        request.setAllergies("Peanuts");
        request.setChronicConditions("Hypertension");
        request.setFamilyIllness("Diabetes");

        request.setLifestyleSmoking("NEVER");
        request.setLifestyleAlcohol("MODERATE");
        request.setActivityLevel("ACTIVE");
        request.setSleepHoursAvg("7-8");

        request.setDoctorEmail("doctor1@example.com");

        return request;
    }

    private DoctorRegisterRequestDomain validDoctorRequest() {
        DoctorRegisterRequestDomain request = new DoctorRegisterRequestDomain();

        request.setEmail("doctor2@example.com");
        request.setPassword("12345678");
        request.setFirstName("Andy");
        request.setLastName("Specter");
        request.setPhoneNumber("+355691234568");
        request.setBirthDate(LocalDate.of(1995, 6, 15));
        request.setGender(GenderEnum.MALE.toString());

        request.setSpecialization("Neurology");
        request.setLicenseNumber("AL-CARD-987655");

        return request;
    }

    @Test
    void registerPatient_validRequest_savesPatient() {
        PatientRegisterRequestDomain request = validPatientRequest();

        Patient patient = new Patient();
        Doctor doctor = new Doctor();

        when(patientRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(doctorRepository.findByEmail(any())).thenReturn(Optional.of(doctor));
        when(patientMapper.toEntity(request)).thenReturn(patient);
        when(passwordEncoder.encode(any())).thenReturn("hashed");
        when(patientRepository.save(any())).thenReturn(patient);
        when(patientMapper.toDomain(patient)).thenReturn(new PatientDomain());

        PatientDomain result = service.registerPatient(request);

        assertNotNull(result);
        verify(patientRepository).save(any());
    }

    @Test
    void registerPatient_existingEmail_throws() {
        PatientRegisterRequestDomain req = validPatientRequest();

        when(patientRepository.findByEmail("patient1@example.com"))
                .thenReturn(Optional.of(new Patient()));

        assertThrows(ResourceAlreadyExistsException.class,
                () -> service.registerPatient(req));
    }

    @Test
    void registerDoctor_valid_savesDoctor() {
        DoctorRegisterRequestDomain req = validDoctorRequest();

        when(doctorRepository.findByEmail(req.getEmail()))
                .thenReturn(Optional.empty());
        when(doctorMapper.toEntity(req)).thenReturn(new Doctor());
        when(passwordEncoder.encode(any())).thenReturn("hash");
        when(doctorRepository.save(any())).thenReturn(new Doctor());
        when(doctorMapper.toDomain(any())).thenReturn(new DoctorDomain());

        assertNotNull(service.registerDoctor(req));
    }

    @Test
    void login_invalidCredentials_throws() {
        LoginRequest req = new LoginRequest("a@b.com", "bad");

        doThrow(BadCredentialsException.class)
                .when(authenticationManager)
                .authenticate(any());

        assertThrows(BadCredentialsException.class,
                () -> service.login(req));
    }

}
