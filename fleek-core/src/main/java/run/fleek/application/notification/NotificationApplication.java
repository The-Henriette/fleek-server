package run.fleek.application.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.notification.FirebaseNotificationService;
import run.fleek.domain.notification.UserNotification;
import run.fleek.domain.notification.UserNotificationService;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.platform.UserPlatformInfo;
import run.fleek.domain.users.platform.UserPlatformInfoService;
import run.fleek.enums.NotificationType;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class NotificationApplication {

  private final UserNotificationService userNotificationService;
  private final FirebaseNotificationService firebaseNotificationService;
  private final UserPlatformInfoService userPlatformInfoService;

  @Transactional
  public void sendNotification(NotificationType notificationType, String notificationKey, String message, FleekUser fleekUser) {
    List<String> pushTokens = userPlatformInfoService.listPlatformInfos(fleekUser).stream()
      .map(UserPlatformInfo::getPushToken)
      .collect(Collectors.toList());

    UserNotification userNotification = userNotificationService.getByNotificationTypeAndNotificationKey(notificationType, notificationKey)
      .orElse(UserNotification.builder()
        .fleekUser(fleekUser)
        .title(notificationType.getMessage())
        .body(message)
        .notificationType(notificationType)
        .notificationKey(notificationKey)
        .build());

    userNotificationService.addNotification(userNotification);

    pushTokens.forEach(token -> firebaseNotificationService.sendNotification(token, notificationType.getMessage(), message, null));
  }
}
