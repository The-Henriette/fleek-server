package run.fleek.application.certification.strategy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.application.profile.ProfileInfoTypeHolder;
import run.fleek.domain.certification.*;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.profile.info.ProfileInfo;
import run.fleek.domain.profile.info.ProfileInfoService;
import run.fleek.domain.profile.type.ProfileInfoType;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.Certification;
import run.fleek.enums.CertificationStatus;
import run.fleek.utils.EmailUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CollegeCertificationHandler implements CertificationHandler {

  private final EmailDomainMetaService emailDomainMetaService;
  private final ProfileService profileService;
  private final ProfileInfoTypeHolder profileInfoTypeHolder;
  private final ProfileInfoService profileInfoService;
  private final UserBadgeService userBadgeService;
  private final UserCertificationService userCertificationService;

  private final String TARGET_PROFILE_INFO_TYPE_CODE = "COLLEGE";

  @Override
  public Certification getCertification() {
    return Certification.COLLEGE;
  }

  @Override
  public void handle(UserCertification userCertification, List<CertificationResource> resources) {
    FleekUser fleekUser = userCertification.getFleekUser();

    List<Profile> profiles = profileService.listProfiles(fleekUser);

    ProfileInfoType profileInfoType = profileInfoTypeHolder.getProfileInfoType(TARGET_PROFILE_INFO_TYPE_CODE);

    CertificationResource certificationResource = resources.get(0);

    Map<Profile, ProfileInfo> preExistentProfileInfos = profileInfoService.listProfileInfosByType(profiles, profileInfoType)
      .stream()
      .collect(Collectors.toMap(ProfileInfo::getProfile, profileInfo -> profileInfo));

    List<ProfileInfo> profileInfoList = profiles.stream()
      .map(profile -> {
        if (preExistentProfileInfos.containsKey(profile)) {
          ProfileInfo preExistentProfileInfo = preExistentProfileInfos.get(profile);
          preExistentProfileInfo.setTypeValue(certificationResource.getResourceContext());
          return preExistentProfileInfo;
        }

        return ProfileInfo.builder()
        .profile(profile)
        .profileInfoCategory(profileInfoType.getProfileInfoCategory())
        .typeCode(profileInfoType.getProfileInfoTypeCode())
        .typeName(profileInfoType.getProfileInfoTypeName())
        .typeOption(null)
        .typeValue(certificationResource.getResourceContext())
        .build();})
      .collect(Collectors.toList());

    profileInfoService.putProfileInfos(profileInfoList);

    userCertification.setCertificationStatus(CertificationStatus.ACCEPTED);
    userCertificationService.putUserCertification(userCertification);
    userBadgeService.putUserBadge(userCertification);
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

    // TODO: 이메일 도메인이 없는 경우 처리
  }
}
