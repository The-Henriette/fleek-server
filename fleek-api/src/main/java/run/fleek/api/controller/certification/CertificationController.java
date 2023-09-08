package run.fleek.api.controller.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.application.certification.CertificationApplication;
import run.fleek.application.notification.NotificationApplication;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.domain.certification.UserCertification;
import run.fleek.domain.certification.dto.CertificationDto;
import run.fleek.domain.certification.dto.CertificationRegisterDto;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.NotificationType;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CertificationController {

  private final CertificationApplication certificationApplication;
  private final NotificationApplication notificationApplication;
  private final FleekUserContext fleekUserContext;

  @PostMapping("/certification/{userCertificationId}/confirm")
  public void confirmCertification(@PathVariable Long userCertificationId) {
    UserCertification certification = certificationApplication.confirmCertification(userCertificationId);

    notificationApplication.sendNotification(
      NotificationType.ofConfirm(certification.getCertificationCode().getName()),
      certification.getUserCertificationId().toString(),
      null,
      certification.getFleekUser()
    );
  }

  @GetMapping("/user/certifications")
  public List<CertificationDto> listUserCertifications() {
    Long userId = fleekUserContext.getUserId();
    return certificationApplication.listUserCertifications(userId);
  }

  @PostMapping("/certification/register")
  public void requestCertification(@RequestBody CertificationRegisterDto certificationRegisterDto) {
    certificationApplication.addCertification(fleekUserContext.fetchUser(), certificationRegisterDto);
  }

}
