package run.fleek.domain.notification;

import run.fleek.application.notification.dto.NotificationPageDto;

public interface UserNotificationRepositoryCustom {
  NotificationPageDto pageNotifications(Long userId, Integer page, Integer size);
}
