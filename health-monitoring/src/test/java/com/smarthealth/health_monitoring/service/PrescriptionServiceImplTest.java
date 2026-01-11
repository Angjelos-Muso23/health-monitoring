package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.mapper.PrescriptionMapper;
import com.smarthealth.health_monitoring.model.domain.PrescriptionDomain;
import com.smarthealth.health_monitoring.model.entity.MedicalHistory;
import com.smarthealth.health_monitoring.model.entity.Prescription;
import com.smarthealth.health_monitoring.repository.MedicalHistoryRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.repository.PrescriptionRepository;
import com.smarthealth.health_monitoring.service.impl.PrescriptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PrescriptionServiceImplTest {

    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;
    @Mock private PrescriptionMapper prescriptionMapper;
    @Mock private PrescriptionRepository prescriptionRepository;
    @Mock private PatientRepository patientRepository;

    @InjectMocks
    private PrescriptionServiceImpl service;

    @Test
    void createPrescription_success() {
        MedicalHistory history = new MedicalHistory();
        Prescription prescription = new Prescription();
        PrescriptionDomain domain = new PrescriptionDomain();

        when(medicalHistoryRepository.findById(1L)).thenReturn(Optional.of(history));
        when(prescriptionMapper.toEntity(domain)).thenReturn(prescription);
        when(prescriptionRepository.save(prescription)).thenReturn(prescription);
        when(prescriptionMapper.toDomain(prescription)).thenReturn(domain);

        PrescriptionDomain result = service.createPrescription(1L, domain);

        assertNotNull(result);
    }

    @Test
    void getPrescriptionsByPatient_success() {
        when(patientRepository.existsById(1L)).thenReturn(true);
        when(prescriptionRepository.findAllByMedicalHistory_Patient_Id(1L))
                .thenReturn(List.of(new Prescription()));
        when(prescriptionMapper.toDomain(any())).thenReturn(new PrescriptionDomain());

        List<PrescriptionDomain> result =
                service.getPrescriptionsByPatient(1L);

        assertEquals(1, result.size());
    }
}

