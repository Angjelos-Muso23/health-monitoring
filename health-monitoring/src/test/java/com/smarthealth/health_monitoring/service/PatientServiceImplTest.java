package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.mapper.PatientMapper;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientRegisterRequestDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientUpdateRequestDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientUserDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.model.enums.BloodTypeEnum;
import com.smarthealth.health_monitoring.model.enums.GenderEnum;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.impl.PatientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PatientServiceImplTest {

    @Mock private PatientRepository patientRepository;
    @Mock private DoctorRepository doctorRepository;
    @Mock private PatientMapper patientMapper;

    @InjectMocks
    private PatientServiceImpl service;

    private Patient validPatient() {
        Patient request = new Patient();
        Doctor doctor = new Doctor();
        doctor.setId(1L);

        request.setEmail("patient1@example.com");
        request.setFirstName("John1");
        request.setLastName("Doe1");
        request.setPhoneNumber("+355691234567");
        request.setBirthDate(LocalDate.of(1995, 6, 15));
        request.setGender(GenderEnum.MALE);

        request.setHeightCm(175);
        request.setWeightKg(72);
        request.setBloodType(BloodTypeEnum.O_POSITIVE);

        request.setAllergies("Peanuts");
        request.setChronicConditions("Hypertension");
        request.setFamilyIllness("Diabetes");

        request.setLifestyleSmoking("NEVER");
        request.setLifestyleAlcohol("MODERATE");
        request.setActivityLevel("ACTIVE");
        request.setSleepHoursAvg("7-8");
        request.setDoctor(doctor);
        return request;
    }

    @Test
    void getPatientById_success() {
        Patient patient = new Patient();
        PatientDomain domain = new PatientDomain();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(patientMapper.toDomain(patient)).thenReturn(domain);

        PatientDomain result = service.getPatientById(1L);

        assertNotNull(result);
    }

    @Test
    void getPatientById_notFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> service.getPatientById(1L));
    }

    @Test
    void getPatientUserDetailsById_success() {
        Patient patient = validPatient();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        PatientUserDomain result = service.getPatientUserDetailsById(1L);

        assertEquals("patient1@example.com", result.getEmail());
    }

    @Test
    void updatePatient_withDoctor() {
        Patient existing = new Patient();
        Doctor doctor = new Doctor();
        PatientUpdateRequestDomain request = new PatientUpdateRequestDomain();
        request.setDoctorEmail("doctor@test.com");

        when(patientRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(doctorRepository.findByEmail("doctor@test.com"))
                .thenReturn(Optional.of(doctor));
        when(patientRepository.save(existing)).thenReturn(existing);
        when(patientMapper.toDomain(existing)).thenReturn(new PatientDomain());

        PatientDomain result = service.updatePatient(1L, request);

        assertNotNull(result);
        assertEquals(doctor, existing.getDoctor());
    }

    @Test
    void deletePatient_success() {
        Patient patient = new Patient();
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        service.deletePatient(1L);

        verify(patientRepository).delete(patient);
    }
}

