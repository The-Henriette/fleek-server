package run.fleek.application.notification.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDataDto {
  private String type;
  private String title;
  private String body;
  private String notificationKey;
}
