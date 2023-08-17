package run.fleek.api.controller.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import run.fleek.api.controller.notification.dto.AdminNotificationDto;
import run.fleek.api.controller.notification.dto.UserPlatformInfoDto;
import run.fleek.application.notification.dto.NotificationPageDto;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.domain.notification.FirebaseNotificationService;
import run.fleek.domain.notification.UserNotificationService;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.platform.UserPlatformInfoService;

@RestController
@RequiredArgsConstructor
public class NotificationController {

  private final FirebaseNotificationService firebaseNotificationService;
  private final UserPlatformInfoService userPlatformInfoService;
  private final FleekUserContext fleekUserContext;
  private final UserNotificationService userNotificationService;

  @GetMapping("/notification")
  public NotificationPageDto pageNotifications(@RequestParam Integer page, @RequestParam Integer size) {
    Long userId = fleekUserContext.getFleekUser().getFleekUserId();
    return userNotificationService.pageNotifications(userId, page, size);
  }

  @PostMapping("/notification/token")
  public void saveToken(@RequestBody UserPlatformInfoDto infoDto) {
    FleekUser fleekUser = fleekUserContext.fetchUser();
    userPlatformInfoService.putPlatformInfo(fleekUser, infoDto.getPlatformType(),
      infoDto.getPushToken(), infoDto.getPreviousPushToken());
  }

  @PostMapping("/notification/admin")
  public void sendNotification(@RequestBody AdminNotificationDto adminNotificationDto) {
    firebaseNotificationService.sendNotification(
      adminNotificationDto.getPushToken(),
      adminNotificationDto.getTitle(),
      adminNotificationDto.getMessage(),
      adminNotificationDto.getData());
  }
}
