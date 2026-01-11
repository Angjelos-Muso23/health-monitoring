package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.mapper.DoctorMapper;
import com.smarthealth.health_monitoring.mapper.PatientMapper;
import com.smarthealth.health_monitoring.model.domain.admin.UsersDomain;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.impl.AdminServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock PatientRepository patientRepository;
    @Mock DoctorRepository doctorRepository;
    @Mock PatientMapper patientMapper;
    @Mock DoctorMapper doctorMapper;

    @InjectMocks AdminServiceImpl service;

    @Test
    void getAllUsers_returnsMappedUsers() {
        Patient p = new Patient();
        Doctor d = new Doctor();

        when(patientRepository.findAll()).thenReturn(List.of(p));
        when(doctorRepository.findAll()).thenReturn(List.of(d));
        when(patientMapper.toDomain(p)).thenReturn(new PatientDomain());
        when(doctorMapper.toDomain(d)).thenReturn(new DoctorDomain());

        UsersDomain result = service.getAllUsers();

        assertEquals(1, result.getPatients().size());
        assertEquals(1, result.getDoctors().size());
    }

    @Test
    void getAllUsers_noUsers_returnsEmptyLists() {
        when(patientRepository.findAll()).thenReturn(List.of());
        when(doctorRepository.findAll()).thenReturn(List.of());

        UsersDomain result = service.getAllUsers();

        assertTrue(result.getPatients().isEmpty());
        assertTrue(result.getDoctors().isEmpty());
    }
}


