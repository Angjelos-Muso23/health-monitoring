package com.smarthealth.health_monitoring.controller;

import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactCreateRequestDomain;
import com.smarthealth.health_monitoring.model.domain.emergencyContact.EmergencyContactDomain;
import com.smarthealth.health_monitoring.service.EmergencyContactService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/emergency-contacts")
@RequiredArgsConstructor
public class EmergencyContactController {

  private final EmergencyContactService emergencyContactService;

  @PreAuthorize("hasAuthority('ROLE_PATIENT') and @securityService.isPatientSelf(#patientId)")
  @PostMapping("/patients/{patientId}")
  public ResponseEntity<EmergencyContactDomain> createEmergencyContact(
      @PathVariable Long patientId, @RequestBody EmergencyContactCreateRequestDomain request) {
    return ResponseEntity.ok(emergencyContactService.createEmergencyContact(patientId, request));
  }

  @PreAuthorize(
      "(hasAuthority('ROLE_DOCTOR') and @securityService.isFamilyDoctor(#patientId)) or (hasAuthority('ROLE_PATIENT') and @securityService.isPatientSelf(#patientId))")
  @GetMapping("/patients/{patientId}")
  public ResponseEntity<List<EmergencyContactDomain>> getPatientEmergencyContacts(
      @PathVariable Long patientId) {
    return ResponseEntity.ok(emergencyContactService.getEmergencyContactsByPatient(patientId));
  }

  @PreAuthorize("hasAuthority('ROLE_PATIENT') and @securityService.isOwnerOfContact(#contactId)")
  @DeleteMapping("/{contactId}")
  public ResponseEntity<Void> deleteEmergencyContact(@PathVariable Long contactId) {
    emergencyContactService.deleteEmergencyContact(contactId);
    return ResponseEntity.noContent().build();
  }
}
