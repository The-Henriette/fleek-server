package run.fleek.api.controller.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.application.certification.CertificationApplication;
import run.fleek.application.notification.NotificationApplication;
import run.fleek.domain.certification.UserCertification;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.NotificationType;

@RestController
@RequiredArgsConstructor
public class CertificationController {

  private final CertificationApplication certificationApplication;
  private final NotificationApplication notificationApplication;

  @PostMapping("/certification/{userCertificationId}/confirm")
  public void confirmCertification(@PathVariable Long userCertificationId) {
    UserCertification certification = certificationApplication.confirmCertification(userCertificationId);

    notificationApplication.sendNotification(
      NotificationType.FACE_CERTIFICATION_ACCEPTED,
      certification.getUserCertificationId().toString(),
      null,
      certification.getFleekUser()
    );
  }
}
