package run.fleek.application.certification;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.certification.strategy.CertificationHandler;
import run.fleek.application.profile.ProfileInfoTypeHolder;
import run.fleek.domain.certification.CertificationResource;
import run.fleek.domain.certification.UserBadgeService;
import run.fleek.domain.certification.UserCertification;
import run.fleek.domain.certification.UserCertificationService;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.profile.image.ProfileImage;
import run.fleek.domain.profile.image.ProfileImageService;
import run.fleek.domain.profile.info.ProfileInfo;
import run.fleek.domain.profile.info.ProfileInfoService;
import run.fleek.domain.profile.type.ProfileInfoType;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.Certification;
import run.fleek.enums.CertificationStatus;
import run.fleek.enums.ImageType;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CertificationProcessor {

  private final ProfileImageService profileImageService;
  private final ProfileService profileService;
  private final ProfileInfoService profileInfoService;
  private final ProfileInfoTypeHolder profileInfoTypeHolder;

  private final List<CertificationHandler> certificationHandlerList;

  private final UserBadgeService userBadgeService;
  private final UserCertificationService userCertificationService;
  private Map<Certification, CertificationHandler> certificationHandlerMap;

  @PostConstruct
  public void init() {
    this.certificationHandlerMap = certificationHandlerList.stream()
      .collect(Collectors.toMap(CertificationHandler::getCertification, Function.identity()));
  }

  private CertificationHandler getCertificationHandler(Certification certification) {
    return certificationHandlerMap.get(certification);
  }

  @Transactional
  public void processCertification(UserCertification userCertification, List<CertificationResource> resources) {
    CertificationHandler certificationHandler = getCertificationHandler(
      userCertification.getCertificationCode());

    certificationHandler.handle(userCertification, resources);

    userCertification.setRejectRead(true);
    userCertification.setCertificationStatus(CertificationStatus.ACCEPTED);
  }

  @Transactional
  public void verifyCertification(UserCertification userCertification, List<CertificationResource> resources) {
    CertificationHandler certificationHandler = getCertificationHandler(
      userCertification.getCertificationCode());

    certificationHandler.verify(userCertification, resources);
  }
}
