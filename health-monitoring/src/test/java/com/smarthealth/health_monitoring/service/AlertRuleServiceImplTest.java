package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.exception.custom.ResourceNotFoundException;
import com.smarthealth.health_monitoring.mapper.AlertRuleMapper;
import com.smarthealth.health_monitoring.model.domain.AlertRuleDomain;
import com.smarthealth.health_monitoring.model.entity.AlertRule;
import com.smarthealth.health_monitoring.model.entity.Patient;
import com.smarthealth.health_monitoring.repository.AlertRuleRepository;
import com.smarthealth.health_monitoring.repository.PatientRepository;
import com.smarthealth.health_monitoring.service.impl.AlertRuleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlertRuleServiceImplTest {

    @Mock
    AlertRuleRepository alertRuleRepository;
    @Mock
    PatientRepository patientRepository;
    @Mock
    AlertRuleMapper alertRuleMapper;

    @InjectMocks
    AlertRuleServiceImpl service;

    @Test
    void createRule_patientExists_createsRule() {
        Patient patient = new Patient();
        AlertRule rule = new AlertRule();
        AlertRuleDomain domain = new AlertRuleDomain();

        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));
        when(alertRuleMapper.toEntity(domain)).thenReturn(rule);
        when(alertRuleRepository.save(rule)).thenReturn(rule);
        when(alertRuleMapper.toDomain(rule)).thenReturn(domain);

        AlertRuleDomain result = service.createRule(1L, domain);

        assertNotNull(result);
        verify(alertRuleRepository).save(rule);
    }

    @Test
    void createRule_patientMissing_throws() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.createRule(1L, new AlertRuleDomain()));
    }

    @Test
    void getRulesByPatient_ruleExists_returnsRule() {
        AlertRule rule = new AlertRule();

        when(patientRepository.existsById(1L)).thenReturn(true);
        when(alertRuleRepository.findByPatient_Id(1L))
                .thenReturn(Optional.of(rule));
        when(alertRuleMapper.toDomain(rule)).thenReturn(new AlertRuleDomain());

        AlertRuleDomain result = service.getRulesByPatient(1L);

        assertNotNull(result);
    }

    @Test
    void getRulesByPatient_patientMissing_throws() {
        when(patientRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.getRulesByPatient(1L));
    }

    @Test
    void updateRule_ruleExists_updates() {
        AlertRule rule = new AlertRule();
        AlertRuleDomain domain = new AlertRuleDomain();

        when(alertRuleRepository.findById(1L)).thenReturn(Optional.of(rule));
        when(alertRuleMapper.toDomain(rule)).thenReturn(domain);

        AlertRuleDomain result = service.updateRule(1L, domain);

        verify(alertRuleMapper).updateEntityFromDomain(domain, rule);
        verify(alertRuleRepository).save(rule);
        assertNotNull(result);
    }

    @Test
    void updateRule_ruleMissing_throws() {
        when(alertRuleRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> service.updateRule(1L, new AlertRuleDomain()));
    }

    @Test
    void deleteRule_ruleExists_deletes() {
        when(alertRuleRepository.existsById(1L)).thenReturn(true);

        service.deleteRule(1L);

        verify(alertRuleRepository).deleteById(1L);
    }

    @Test
    void deleteRule_ruleMissing_throws() {
        when(alertRuleRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> service.deleteRule(1L));
    }

}