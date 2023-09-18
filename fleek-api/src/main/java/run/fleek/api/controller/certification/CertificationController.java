package run.fleek.api.controller.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import run.fleek.application.certification.CertificationApplication;
import run.fleek.application.certification.dto.CertificationRejectDto;
import run.fleek.application.notification.NotificationApplication;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.domain.certification.UserCertification;
import run.fleek.domain.certification.dto.CertificationDto;
import run.fleek.domain.certification.dto.CertificationRegisterDto;
import run.fleek.domain.certification.dto.UserCertificationDto;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.verification.UserVerificationService;
import run.fleek.enums.Certification;
import run.fleek.enums.CertificationStatus;
import run.fleek.enums.NotificationType;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CertificationController {

  private final CertificationApplication certificationApplication;
  private final NotificationApplication notificationApplication;
  private final FleekUserContext fleekUserContext;
  private final UserVerificationService userVerificationService;

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

  @PostMapping("/certification/{userCertificationId}/reject")
  public void rejectCertification(@PathVariable Long userCertificationId, @RequestBody CertificationRejectDto rejectDto) {
    UserCertification certification = certificationApplication.rejectCertification(userCertificationId, rejectDto);

    notificationApplication.sendNotification(
      NotificationType.ofReject(certification.getCertificationCode().getName()),
      certification.getUserCertificationId().toString(),
      rejectDto.getRejectReasonDetail(),
      certification.getFleekUser()
    );
  }

  @GetMapping("/certification/email/{verificationCode}")
  public void confirmCertificationByEmail(@PathVariable String verificationCode) {
    UserCertification userCertification = certificationApplication.confirmCertificationByEmail(verificationCode);

    if (userCertification.getCertificationStatus().equals(CertificationStatus.ACCEPTED)) {
      notificationApplication.sendNotification(
        NotificationType.ofConfirm(userCertification.getCertificationCode().getName()),
        userCertification.getUserCertificationId().toString(),
        null,
        userCertification.getFleekUser()
      );
    }
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

  @GetMapping("/certification/{certificationType}/user")
  public UserCertificationDto getUserCertification(@PathVariable String certificationType) {
    Certification certification = Certification.valueOf(certificationType.toUpperCase());
    FleekUser fleekUser = fleekUserContext.fetchUser();

    return certificationApplication.getUserCertification(fleekUser, certification);
  }
}
