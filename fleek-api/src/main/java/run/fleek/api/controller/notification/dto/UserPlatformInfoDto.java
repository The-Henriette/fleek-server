package run.fleek.api.controller.notification.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPlatformInfoDto {
  private String pushToken;
  private String previousPushToken;
  private String platformType;
}
