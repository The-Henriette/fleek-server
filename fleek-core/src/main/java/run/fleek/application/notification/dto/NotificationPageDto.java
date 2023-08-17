package run.fleek.application.notification.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPageDto {
  private Integer page;
  private Long totalSize;
  private List<NotificationDataDto> notifications;
}
