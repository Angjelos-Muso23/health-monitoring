package com.smarthealth.health_monitoring.mapper;

import com.smarthealth.health_monitoring.common.MapperConfig;
import com.smarthealth.health_monitoring.model.domain.NotificationDomain;
import com.smarthealth.health_monitoring.model.entity.Notification;
import org.mapstruct.*;

@Mapper(config = MapperConfig.class)
public interface NotificationMapper {

  @Mapping(target = "createDate", ignore = true)
  @Mapping(target = "modifyDate", ignore = true)
  @Mapping(target = "alert", ignore = true)
  Notification toEntity(NotificationDomain domain);

  @Mapping(target = "alertId", source = "alert.id")
  NotificationDomain toDomain(Notification entity);
}
