package com.smarthealth.health_monitoring.model.domain;

import com.smarthealth.health_monitoring.model.enums.NotificationChannelEnum;
import com.smarthealth.health_monitoring.model.enums.NotificationStatusEnum;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDomain {

  private Long alertId;
  private NotificationChannelEnum channel;
  private NotificationStatusEnum status;
  private Date sentAt;
}
