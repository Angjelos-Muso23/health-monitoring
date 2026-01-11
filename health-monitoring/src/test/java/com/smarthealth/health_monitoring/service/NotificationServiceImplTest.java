package com.smarthealth.health_monitoring.service;

import com.smarthealth.health_monitoring.mapper.AlertMapper;
import com.smarthealth.health_monitoring.mapper.NotificationMapper;
import com.smarthealth.health_monitoring.model.domain.NotificationDomain;
import com.smarthealth.health_monitoring.model.entity.Notification;
import com.smarthealth.health_monitoring.model.enums.NotificationStatusEnum;
import com.smarthealth.health_monitoring.repository.NotificationRepository;
import com.smarthealth.health_monitoring.service.impl.NotificationServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationServiceImplTest {

    @Mock
    private NotificationRepository notificationRepository;
    @Mock private AlertMapper alertMapper;
    @Mock private NotificationMapper notificationMapper;

    @InjectMocks
    private NotificationServiceImpl service;

    @Test
    void getMyNotifications_authenticated_returnsList() {
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(true);
        when(auth.getName()).thenReturn("patient@example.com");

        SecurityContext context = mock(SecurityContext.class);
        when(context.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(context);

        when(notificationRepository.findAllByAlert_Patient_Email(any()))
                .thenReturn(List.of(new Notification()));
        when(notificationMapper.toDomain(any())).thenReturn(new NotificationDomain());

        List<NotificationDomain> result = service.getMyNotifications();

        assertEquals(1, result.size());
    }

    @Test
    void markAsRead_setsStatus() {
        Notification notification = new Notification();

        when(notificationRepository.findById(1L))
                .thenReturn(Optional.of(notification));

        service.markAsRead(1L);

        assertEquals(NotificationStatusEnum.READ, notification.getStatus());
    }
}

