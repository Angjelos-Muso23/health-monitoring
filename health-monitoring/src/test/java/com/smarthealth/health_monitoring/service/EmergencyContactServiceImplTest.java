package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.EmergencyContactMapper;
import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactCreateRequestDomain;
import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactDomain;
import com.smarthealth.health_monitoring.model.entity.EmergencyContact;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.EmergencyContactRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.impl.EmergencyContactServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmergencyContactServiceImplTest {

    @Mock
    private PatientRepository patientRepository;
    @Mock private EmergencyContactRepository emergencyContactRepository;
    @Mock private EmergencyContactMapper emergencyContactMapper;

    @InjectMocks
    private EmergencyContactServiceImpl service;

    @Test
    void createEmergencyContact_patientExists_saves() {
        Patient patient = new Patient();
        EmergencyContact contact = new EmergencyContact();
        EmergencyContactDomain domain = new EmergencyContactDomain();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(emergencyContactMapper.toEntity(any())).thenReturn(contact);
        when(emergencyContactRepository.save(contact)).thenReturn(contact);
        when(emergencyContactMapper.toDomain(contact)).thenReturn(domain);

        EmergencyContactDomain result =
                service.createEmergencyContact(1L, new EmergencyContactCreateRequestDomain());

        assertNotNull(result);
    }

    @Test
    void createEmergencyContact_patientNotFound_throws() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.createEmergencyContact(1L, new EmergencyContactCreateRequestDomain()));
    }

    @Test
    void getEmergencyContactsByPatient_exists_returnsList() {
        when(patientRepository.existsById(1L)).thenReturn(true);
        when(emergencyContactRepository.findAllByPatient_Id(1L))
                .thenReturn(List.of(new EmergencyContactDomain()));

        List<EmergencyContactDomain> result =
                service.getEmergencyContactsByPatient(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getEmergencyContactsByPatient_notFound_throws() {
        when(patientRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.getEmergencyContactsByPatient(1L));
    }

    @Test
    void deleteEmergencyContact_exists_deletes() {
        EmergencyContact contact = new EmergencyContact();

        when(emergencyContactRepository.findById(1L))
                .thenReturn(Optional.of(contact));

        service.deleteEmergencyContact(1L);

        verify(emergencyContactRepository).delete(contact);
    }
}
