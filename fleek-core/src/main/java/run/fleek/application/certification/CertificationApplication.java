package run.fleek.application.certification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.domain.certification.*;
import run.fleek.domain.certification.dto.CertificationRegisterDto;
import run.fleek.domain.users.FleekUser;

@Component
@RequiredArgsConstructor
public class CertificationApplication {

  private final CertificationService certificationService;
  private final CertificationResourceService certificationResourceService;
  private final UserCertificationService userCertificationService;

  public void addCertification(FleekUser fleekUser, CertificationRegisterDto dto) {
    Certification targetCertification = certificationService.getCertificationByCode(dto.getCertificationCode())
      .orElseThrow();
    UserCertification userCertification = userCertificationService.addUserCertification(new UserCertification(targetCertification, fleekUser));
    certificationResourceService.addCertificationResources(CertificationResource.from(dto.getResources(), userCertification));
  }
}
