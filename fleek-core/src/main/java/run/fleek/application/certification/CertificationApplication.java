package run.fleek.application.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.common.exception.FleekException;
import run.fleek.domain.certification.*;
import run.fleek.domain.certification.dto.CertificationRegisterDto;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.CertificationStatus;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CertificationApplication {

  private final CertificationService certificationService;
  private final CertificationResourceService certificationResourceService;
  private final UserCertificationService userCertificationService;
  private final CertificationProcessor certificationProcessor;

  @Transactional
  public void addCertification(FleekUser fleekUser, CertificationRegisterDto dto) {
    Certification targetCertification = certificationService.getCertificationByCode(dto.getCertificationCode())
      .orElseThrow();
    UserCertification userCertification = userCertificationService.addUserCertification(new UserCertification(targetCertification, fleekUser));
    certificationResourceService.addCertificationResources(CertificationResource.from(dto.getResources(), userCertification));
  }

  @Transactional
  public UserCertification confirmCertification(Long userCertificationId) {
    UserCertification userCertification = userCertificationService.getUserCertification(userCertificationId)
      .orElseThrow(new FleekException("유효하지 않은 인증입니다."));

    List<CertificationResource> resources = certificationResourceService.listCertificationResources(userCertification);

    certificationProcessor.processCertification(userCertification, resources);

    resources.forEach(r -> r.setCertificationStatus(CertificationStatus.ACCEPTED));
    certificationResourceService.addCertificationResources(resources);

    userCertification.setCertificationStatus(CertificationStatus.ACCEPTED);
    userCertificationService.addUserCertification(userCertification);

    return userCertification;
  }
}
