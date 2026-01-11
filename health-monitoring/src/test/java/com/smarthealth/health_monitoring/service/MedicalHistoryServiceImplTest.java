package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.MedicalHistoryMapper;
import com.smarthealth.health_monitoring.model.domain.MedicalHistoryDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.MedicalHistory;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.MedicalHistoryRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.impl.MedicalHistoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MedicalHistoryServiceImplTest {

    @Mock private PatientRepository patientRepository;
    @Mock private DoctorRepository doctorRepository;
    @Mock private MedicalHistoryMapper medicalHistoryMapper;
    @Mock private MedicalHistoryRepository medicalHistoryRepository;

    @InjectMocks private MedicalHistoryServiceImpl service;

    @Test
    void createMedicalHistory_valid_saves() {
        Patient patient = new Patient();
        Doctor doctor = new Doctor();
        MedicalHistory history = new MedicalHistory();
        MedicalHistoryDomain domain = new MedicalHistoryDomain();
        domain.setDoctorId(1L);

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(medicalHistoryMapper.toEntity(domain)).thenReturn(history);
        when(medicalHistoryRepository.save(history)).thenReturn(history);
        when(medicalHistoryMapper.toDomain(history)).thenReturn(domain);

        MedicalHistoryDomain result =
                service.createMedicalHistory(1L, domain);

        assertNotNull(result);
    }

    @Test
    void getMedicalHistoryByPatient_success() {
        Long patientId = 1L;

        MedicalHistory history = new MedicalHistory();
        MedicalHistoryDomain domain = new MedicalHistoryDomain();

        when(patientRepository.existsById(patientId)).thenReturn(true);
        when(medicalHistoryRepository.findAllByPatient_Id(patientId))
                .thenReturn(List.of(history));
        when(medicalHistoryMapper.toDomain(history)).thenReturn(domain);

        List<MedicalHistoryDomain> result =
                service.getMedicalHistoryByPatient(patientId);

        assertEquals(1, result.size());
    }

    @Test
    void getMedicalHistoryByPatient_patientNotFound() {
        Long patientId = 1L;
        when(patientRepository.existsById(patientId)).thenReturn(false);

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.getMedicalHistoryByPatient(patientId));
    }
}

