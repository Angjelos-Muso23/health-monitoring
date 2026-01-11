package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.DoctorMapper;
import com.smarthealth.health_monitoring.mapper.PatientMapper;
import com.smarthealth.health_monitoring.model.domain.doctor.DoctorDomain;
import com.smarthealth.health_monitoring.model.domain.patient.PatientDomain;
import com.smarthealth.health_monitoring.model.entity.Doctor;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.DoctorRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.impl.DoctorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DoctorServiceImplTest {

    @Mock private DoctorRepository doctorRepository;
    @Mock private PatientRepository patientRepository;
    @Mock private DoctorMapper doctorMapper;
    @Mock private PatientMapper patientMapper;

    @InjectMocks private DoctorServiceImpl service;

    @Test
    void getDoctorById_found_returnsDomain() {
        Doctor doctor = new Doctor();
        doctor.setId(1L);

        DoctorDomain domain = new DoctorDomain();

        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));
        when(doctorMapper.toDomain(doctor)).thenReturn(domain);

        DoctorDomain result = service.getDoctorById(1L);

        assertNotNull(result);
    }

    @Test
    void getDoctorById_notFound_throws() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getDoctorById(1L));
    }

    @Test
    void getPatientsByDoctor_doctorExists_returnsPatients() {
        when(doctorRepository.existsById(1L)).thenReturn(true);

        Patient patient = new Patient();
        PatientDomain domain = new PatientDomain();

        when(patientRepository.findAllByDoctor_Id(1L)).thenReturn(List.of(patient));
        when(patientMapper.toDomain(patient)).thenReturn(domain);

        List<PatientDomain> result = service.getPatientsByDoctor(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getPatientsByDoctor_doctorNotFound_throws() {
        when(doctorRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.getPatientsByDoctor(1L));
    }

    @Test
    void deleteDoctor_exists_deletes() {
        when(doctorRepository.existsById(1L)).thenReturn(true);

        service.deleteDoctor(1L);

        verify(doctorRepository).deleteById(1L);
    }

    @Test
    void deleteDoctor_notFound_throws() {
        when(doctorRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteDoctor(1L));
    }
}

