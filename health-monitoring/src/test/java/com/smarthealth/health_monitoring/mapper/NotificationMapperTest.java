package com.smarthealth.health_monitoring.mapper;
import static org.assertj.core.api.Assertions.*;

import com.smarthealth.health_monitoring.model.domain.NotificationDomain;
import com.smarthealth.health_monitoring.model.entity.Alert;
import com.smarthealth.health_monitoring.model.entity.Notification;
import com.smarthealth.health_monitoring.model.enums.NotificationStatusEnum;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class NotificationMapperTest {

    private final NotificationMapper mapper =
            Mappers.getMapper(NotificationMapper.class);

    @Test
    void toEntity_shouldIgnoreAlertAndDates() {
        NotificationDomain domain = NotificationDomain.builder()
                .status(NotificationStatusEnum.DELIVERED)
                .build();

        Notification entity = mapper.toEntity(domain);

        assertThat(entity.getStatus()).isEqualTo(NotificationStatusEnum.DELIVERED);
        assertThat(entity.getAlert()).isNull();
        assertThat(entity.getCreateDate()).isNull();
        assertThat(entity.getModifyDate()).isNull();
    }

    @Test
    void toDomain_shouldMapAlertId() {
        Alert alert = new Alert();
        alert.setId(7L);

        Notification entity = new Notification();
        entity.setAlert(alert);

        NotificationDomain domain = mapper.toDomain(entity);

        assertThat(domain.getAlertId()).isEqualTo(7L);
    }
}

