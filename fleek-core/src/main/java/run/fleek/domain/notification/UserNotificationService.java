package run.fleek.domain.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.notification.dto.NotificationPageDto;
import run.fleek.enums.NotificationType;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserNotificationService {

  private final UserNotificationRepository userNotificationRepository;


  @Transactional(readOnly = true)
  public Optional<UserNotification> getByNotificationTypeAndNotificationKey(NotificationType notificationType, String notificationKey) {
    return userNotificationRepository.findByNotificationTypeAndNotificationKey(notificationType, notificationKey);
  }

  @Transactional
  public void addNotification(UserNotification userNotification) {
    userNotificationRepository.save(userNotification);
  }

  @Transactional(readOnly = true)
  public NotificationPageDto pageNotifications(Long userId, Integer page, Integer size) {
    return userNotificationRepository.pageNotifications(userId, page, size);
  }
}
