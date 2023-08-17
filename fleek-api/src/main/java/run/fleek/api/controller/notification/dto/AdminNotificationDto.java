package run.fleek.api.controller.notification.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminNotificationDto {
  private String pushToken;
  private String title;
  private String message;
  private Map<String, String> data;
}
