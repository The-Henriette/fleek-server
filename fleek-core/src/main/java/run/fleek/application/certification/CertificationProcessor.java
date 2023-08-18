package run.fleek.application.certification;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.profile.ProfileInfoTypeHolder;
import run.fleek.domain.certification.CertificationResource;
import run.fleek.domain.certification.UserCertification;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.profile.image.ProfileImage;
import run.fleek.domain.profile.image.ProfileImageService;
import run.fleek.domain.profile.info.ProfileInfo;
import run.fleek.domain.profile.info.ProfileInfoService;
import run.fleek.domain.profile.type.ProfileInfoType;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.ImageType;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CertificationProcessor {

  private final ProfileImageService profileImageService;
  private final ProfileService profileService;
  private final ProfileInfoService profileInfoService;
  private final ProfileInfoTypeHolder profileInfoTypeHolder;

  @Transactional
  public void processCertification(UserCertification userCertification, List<CertificationResource> resources) {
    FleekUser fleekUser = userCertification.getFleekUser();

    List<Profile> profiles = profileService.listProfiles(fleekUser);

    if (userCertification.getCertificationCode().equals("FACE")) {

      profileImageService.removeAllFaceImageByUser(fleekUser);
      List<ProfileImage> facePics = Lists.newArrayList();

      profiles.forEach(profile -> {
        int count = 0;
        for (CertificationResource resource : resources) {
          ProfileImage profileImage = ProfileImage.builder()
            .profile(profile)
            .imageType(ImageType.FACE_IMAGE)
            .imageUrl(resource.getResourceContext())
            .orderNumber(count++)
            .build();
          facePics.add(profileImage);
        }
      });

      profileImageService.addProfileImageList(facePics);
    }

    if (userCertification.getCertificationCode().equals("COMPANY")) {
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

    if (userCertification.getCertificationCode().equals("INBODY")) {
      // TODO: profileInfoType 관련 처리 필요
    }

    if (userCertification.getCertificationCode().equals("COLLEGE")) {
      // TODO: profileInfoType 관련 처리 필요
    }
  }
}
