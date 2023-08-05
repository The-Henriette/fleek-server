package run.fleek.application.certification;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.certification.CertificationResource;
import run.fleek.domain.certification.UserCertification;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.profile.image.ProfileImage;
import run.fleek.domain.profile.image.ProfileImageService;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.ImageType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CertificationProcessor {

  private final ProfileImageService profileImageService;
  private final ProfileService profileService;

  @Transactional
  public void processCertification(UserCertification userCertification, List<CertificationResource> resources) {
    if (userCertification.getCertificationCode().equals("FACE")) {
      FleekUser fleekUser = userCertification.getFleekUser();

      List<Profile> profiles = profileService.listProfiles(fleekUser);

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
  }
}
