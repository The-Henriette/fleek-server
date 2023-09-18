package run.fleek.application.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.certification.dto.CertificationRejectDto;
import run.fleek.application.certification.dto.RekognitionResponseDto;
import run.fleek.application.notification.NotificationApplication;
import run.fleek.common.email.EmailService;
import run.fleek.common.exception.FleekException;
import run.fleek.common.notification.DevCertificationNotifier;
import run.fleek.domain.certification.*;
import run.fleek.domain.certification.dto.CertificationDto;
import run.fleek.domain.certification.dto.CertificationRegisterDto;
import run.fleek.domain.certification.dto.UserCertificationDto;
import run.fleek.domain.users.FleekUser;
import run.fleek.domain.users.verification.UserVerification;
import run.fleek.domain.users.verification.UserVerificationService;
import run.fleek.enums.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CertificationApplication {

  private final CertificationResourceService certificationResourceService;
  private final UserCertificationService userCertificationService;
  private final CertificationProcessor certificationProcessor;
  private final CertificationHolder certificationHolder;
  private final DevCertificationNotifier devCertificationNotifier;
  private final EmailService emailService;
  private final UserBadgeService userBadgeService;
  private final UserVerificationService userVerificationService;
  private final AwsRekognitionWrapper awsRekognitionWrapper;
  private final NotificationApplication notificationApplication;

  @Transactional
  public UserCertification addCertification(FleekUser fleekUser, CertificationRegisterDto dto) {
    Certification targetCertification = certificationHolder.getCertification(dto.getCertificationCode());
    UserCertification userCertification =
      userCertificationService.addUserCertification(new UserCertification(targetCertification, fleekUser, CertificationMethod.valueOf(dto.getCertificationMethod())));
    List<CertificationResource> resources =
      certificationResourceService.addCertificationResources(CertificationResource.from(dto.getResources(), userCertification));

    if (dto.getCertificationMethod().equals(CertificationMethod.EMAIL.name())) {
      UserVerification emailVerification = userVerificationService.addUserVerification(VerificationType.CERTIFICATION, fleekUser, userCertification.getUserCertificationId());

      emailService.send(dto.getResources().get(0).getResourceUrl(), String.format("[Fleek] %s 메일입니다.", targetCertification.getName()), String.format("아래 링크를 클릭해 인증을 완료해주세요\n%s", "https://api.fleek.run/certification/email/" + emailVerification.getVerificationCode()));
    }
    devCertificationNotifier.sendNotification(String.format("신규 인증 요청 추가 - 유저번호 :  %s, 인증유형: %s, 인증요청ID: %s", fleekUser.getPhoneNumber(), userCertification.getCertificationCode(), userCertification.getUserCertificationId().toString()));

    return userCertification;
  }

  @Transactional
  public UserCertification confirmCertification(Long userCertificationId) {
    UserCertification userCertification = userCertificationService.getUserCertification(userCertificationId)
      .orElseThrow(new FleekException("유효하지 않은 인증입니다."));

    List<CertificationResource> resources = certificationResourceService.listCertificationResources(userCertification);

    certificationProcessor.processCertification(userCertification, resources);

    resources.forEach(r -> r.setCertificationStatus(CertificationStatus.ACCEPTED));
    certificationResourceService.addCertificationResources(resources);


    userBadgeService.putUserBadge(userCertification);

    return userCertification;
  }

  @Transactional(readOnly = true)
  public List<CertificationDto> listUserCertifications(Long userId) {
    return userCertificationService.listUserCertifications(userId).stream()
      .map(cer -> CertificationDto.builder()
        .certificationCode(cer.getCertificationCode().getName())
        .certificationName(cer.getCertificationCode().getName())
        .certificationDescription(cer.getCertificationCode().getDescription())
        .certificationStatus(cer.getCertificationStatus().name())
        .rejectRead(cer.getRejectRead())
        .build())
      .collect(Collectors.toList());
  }

  @Transactional
  public void processFaceCertification(UserCertification userCertification) {
    List<CertificationResource> resources = certificationResourceService.listCertificationResources(userCertification);

    String source = resources.get(0).getResourceUrl();
    String target = resources.get(0).getResourceContext();

    RekognitionResponseDto response = awsRekognitionWrapper.compareFaces(source, target);

    if (response.getSamePerson()) {
      this.confirmCertification(userCertification.getUserCertificationId());
      notificationApplication.sendNotification(
        NotificationType.ofConfirm(userCertification.getCertificationCode().getName()),
        userCertification.getUserCertificationId().toString(),
        null,
        userCertification.getFleekUser()
      );
    } else {
       UserCertification certification = this.rejectCertification(userCertification.getUserCertificationId(), CertificationRejectDto.builder()
         .rejectReason("교환용 사진 본인 인증에 실패했습니다.")
         .rejectReasonDetail("교환용 사진 본인 인증에 실패했습니다.")
         .build());

      notificationApplication.sendNotification(
        NotificationType.ofReject(certification.getCertificationCode().getName()),
        certification.getUserCertificationId().toString(),
        "교환용 사진 본인 인증에 실패했습니다.",
        certification.getFleekUser()
      );

    }
  }

  @Transactional
  public UserCertification confirmCertificationByEmail(String verificationCode) {
    UserVerification userVerification = userVerificationService.getVerificationByCode(verificationCode)
      .orElseThrow(new FleekException("유효하지 않은 인증입니다."));

    UserCertification userCertification = userCertificationService.getUserCertification(userVerification.getUserCertificationId())
      .orElseThrow(new FleekException("유효하지 않은 인증입니다."));

    List<CertificationResource> resources = certificationResourceService.listCertificationResources(userCertification);
    resources.forEach(r -> r.setCertificationStatus(CertificationStatus.ACCEPTED));
    certificationResourceService.addCertificationResources(resources);

    certificationProcessor.verifyCertification(userCertification, resources);

    if (userCertification.getCertificationStatus().equals(CertificationStatus.ACCEPTED)) {
      userBadgeService.putUserBadge(userCertification);
    }

    return userCertification;
  }

  public UserCertificationDto getUserCertification(FleekUser fleekUser, Certification certification) {
    Optional<UserCertification> userCertificationOpt = userCertificationService.getUserCertificationByUserAndType(fleekUser, certification);
    if (userCertificationOpt.isPresent()) {
      CertificationResource resource =
        certificationResourceService.listCertificationResources(userCertificationOpt.get()).get(0);

      userCertificationService.resolveRejection(userCertificationOpt.get());

      return UserCertificationDto.from(userCertificationOpt.get(), resource);
    }

    return UserCertificationDto.builder()
      .build();
  }

  @Transactional
  public UserCertification rejectCertification(Long userCertificationId, CertificationRejectDto rejectDto) {
    UserCertification userCertification = userCertificationService.getUserCertification(userCertificationId)
      .orElseThrow(new FleekException("유효하지 않은 인증입니다."));
    userCertification.setCertificationStatus(CertificationStatus.REJECTED);
    userCertification.setRejectRead(false);

    userCertificationService.putUserCertification(userCertification);

    List<CertificationResource> resources =
      certificationResourceService.listCertificationResources(userCertification);
    resources.forEach(
      r -> {
        r.setCertificationStatus(CertificationStatus.REJECTED);
        r.setRejectReason(rejectDto.getRejectReason());
        r.setRejectReasonDetail(rejectDto.getRejectReasonDetail());
      }
    );

    certificationResourceService.addCertificationResources(resources);

    return userCertification;
  }
}
