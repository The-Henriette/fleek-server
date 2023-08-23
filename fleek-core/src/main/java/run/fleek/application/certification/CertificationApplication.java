package run.fleek.application.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.common.email.EmailService;
import run.fleek.common.exception.FleekException;
import run.fleek.common.notification.DevCertificationNotifier;
import run.fleek.domain.certification.*;
import run.fleek.domain.certification.dto.CertificationDto;
import run.fleek.domain.certification.dto.CertificationRegisterDto;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.CertificationMethod;
import run.fleek.enums.CertificationStatus;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CertificationApplication {

  private final CertificationService certificationService;
  private final CertificationResourceService certificationResourceService;
  private final UserCertificationService userCertificationService;
  private final CertificationProcessor certificationProcessor;
  private final CertificationHolder certificationHolder;
  private final DevCertificationNotifier devCertificationNotifier;
  private final EmailService emailService;

  @Transactional
  public void addCertification(FleekUser fleekUser, CertificationRegisterDto dto) {
    Certification targetCertification = certificationHolder.getCertification(dto.getCertificationCode());
    UserCertification userCertification =
      userCertificationService.addUserCertification(new UserCertification(targetCertification, fleekUser, CertificationMethod.valueOf(dto.getCertificationMethod())));
    List<CertificationResource> resources =
      certificationResourceService.addCertificationResources(CertificationResource.from(dto.getResources(), userCertification));

    String resourceCode = resources.get(0).getResourceCode();

    if (dto.getCertificationMethod().equals(CertificationMethod.EMAIL.name())) {
      emailService.send(dto.getResources().get(0).getResourceUrl(), String.format("[Fleek] %s 메일입니다.", targetCertification.getCertificationName()), String.format("아래 링크를 클릭해 인증을 완료해주세요\n%s", "https://api.fleek.run/certification/resource/" + resourceCode));
    }
    devCertificationNotifier.sendNotification(String.format("신규 인증 요청 추가 - 유저번호 :  %s, 인증유형: %s, 인증요청ID: %s", fleekUser.getPhoneNumber(), userCertification.getCertificationCode(), userCertification.getUserCertificationId().toString()));
  }

  @Transactional
  public UserCertification confirmCertification(Long userCertificationId) {
    UserCertification userCertification = userCertificationService.getUserCertification(userCertificationId)
      .orElseThrow(new FleekException("유효하지 않은 인증입니다."));

    List<CertificationResource> resources = certificationResourceService.listCertificationResources(userCertification);

    certificationProcessor.processCertification(userCertification, resources);

    resources.forEach(r -> r.setCertificationStatus(CertificationStatus.ACCEPTED));
    certificationResourceService.addCertificationResources(resources);

    Optional<UserCertification> activeCertificationOpt = userCertificationService.getActiveCertification(
      userCertification.getFleekUser(), userCertification.getCertificationCode()
    );

    if (activeCertificationOpt.isPresent() && !activeCertificationOpt.get().getUserCertificationId().equals(userCertificationId)) {
      UserCertification activeCertification = activeCertificationOpt.get();
      activeCertification.setActive(false);
      userCertificationService.addUserCertification(activeCertification);
    }

    userCertification.setCertificationStatus(CertificationStatus.ACCEPTED);
    userCertification.setActive(true);
    userCertificationService.addUserCertification(userCertification);

    return userCertification;
  }

  @Transactional(readOnly = true)
  public List<CertificationDto> listUserCertifications(Long userId) {
    return userCertificationService.listUserCertifications(userId).stream()
      .map(cer -> CertificationDto.builder()
        .certificationCode(cer.getCertificationCode())
        .certificationName(certificationHolder.getCertification(cer.getCertificationCode()).getCertificationName())
        .certificationDescription(certificationHolder.getCertification(cer.getCertificationCode()).getCertificationDescription())
        .certificationStatus(cer.getCertificationStatus().name())
        .build())
      .collect(Collectors.toList());
  }
}
