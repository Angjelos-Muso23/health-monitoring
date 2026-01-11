package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.AlertMapper;
import com.smarthealth.health_monitoring.model.domain.AlertDomain;
import com.smarthealth.health_monitoring.model.entity.Alert;
import com.smarthealth.health_monitoring.model.entity.Measurement;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.AlertRepository;
import com.smarthealth.health_monitoring.repository.AlertRuleRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.impl.AlertServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlertServiceImplTest {

    @Mock PatientRepository patientRepository;
    @Mock
    AlertRepository alertRepository;
    @Mock
    AlertMapper alertMapper;
    @Mock AlertRuleRepository alertRuleRepository;
    @Mock NotificationService notificationService;

    @InjectMocks
    AlertServiceImpl service;

    @Test
    void getAlertsByPatient_patientExists_returnsAlerts() {
        Alert alert = new Alert();

        when(patientRepository.existsById(1L)).thenReturn(true);
        when(alertRepository.findAllByPatient_Id(1L)).thenReturn(List.of(alert));
        when(alertMapper.toDomain(alert)).thenReturn(new AlertDomain());

        List<AlertDomain> result = service.getAlertsByPatient(1L);

        assertEquals(1, result.size());
    }

    @Test
    void getAlertsByPatient_patientMissing_throws() {
        when(patientRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.getAlertsByPatient(1L));
    }

    @Test
    void getAlertById_exists_returnsAlert() {
        Alert alert = new Alert();

        when(alertRepository.findById(1L)).thenReturn(Optional.of(alert));
        when(alertMapper.toDomain(alert)).thenReturn(new AlertDomain());

        assertNotNull(service.getAlertById(1L));
    }

    @Test
    void getAlertById_missing_throws() {
        when(alertRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.getAlertById(1L));
    }

    @Test
    void evaluate_ruleMissing_throws() {
        Measurement m = new Measurement();
        Patient p = new Patient();
        p.setId(1L);
        m.setPatient(p);

        when(alertRuleRepository.findByPatient_Id(1L))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.evaluate(m));
    }

}
