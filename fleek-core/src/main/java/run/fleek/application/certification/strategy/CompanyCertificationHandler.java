package run.fleek.application.certification.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.application.profile.ProfileInfoTypeHolder;
import run.fleek.domain.certification.CertificationResource;
import run.fleek.domain.certification.EmailDomainMeta;
import run.fleek.domain.certification.EmailDomainMetaService;
import run.fleek.domain.certification.UserCertification;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.profile.image.ProfileImageService;
import run.fleek.domain.profile.info.ProfileInfo;
import run.fleek.domain.profile.info.ProfileInfoService;
import run.fleek.domain.profile.type.ProfileInfoType;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.Certification;
import run.fleek.utils.EmailUtil;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CompanyCertificationHandler implements CertificationHandler {
  private final ProfileService profileService;
  private final ProfileImageService profileImageService;

  private final ProfileInfoService profileInfoService;
  private final ProfileInfoTypeHolder profileInfoTypeHolder;
  private final EmailDomainMetaService emailDomainMetaService;

  @Override
  public Certification getCertification() {
    return Certification.COMPANY;
  }

  @Override
  public void handle(UserCertification userCertification, List<CertificationResource> resources) {
    FleekUser fleekUser = userCertification.getFleekUser();

    List<Profile> profiles = profileService.listProfiles(fleekUser);


    ProfileInfoType profileInfoType = profileInfoTypeHolder.getProfileInfoType("COMPANY");
    CertificationResource certificationResource = resources.get(0);
    List<ProfileInfo> profileInfoList = profiles.stream()
      .map(profile -> ProfileInfo.builder()
        .profile(profile)
        .profileInfoCategory(profileInfoType.getProfileInfoCategory())
        .typeCode(profileInfoType.getProfileInfoTypeCode())
        .typeName(profileInfoType.getProfileInfoTypeName())
        .typeOption(null)
        .typeValue(certificationResource.getResourceContext())
        .build())
      .collect(Collectors.toList());

    profileInfoService.putProfileInfos(profileInfoList);

  }

  @Override
  public void verify(UserCertification userCertification, List<CertificationResource> resources) {
    String emailDomain = EmailUtil.extractDomainFromEmail(resources.get(0).getResourceUrl());

    Optional<EmailDomainMeta> emailDomainMetaOptional =
      emailDomainMetaService.getByDomainAndCertificationCode(emailDomain, userCertification.getCertificationCode());

    if (emailDomainMetaOptional.isPresent()) {
      resources.get(0).setResourceContext(emailDomainMetaOptional.get().getTargetName());
      this.handle(userCertification, resources);
    }
  }
}
