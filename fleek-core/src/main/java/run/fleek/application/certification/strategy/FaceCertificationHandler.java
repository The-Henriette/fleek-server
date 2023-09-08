package run.fleek.application.certification.strategy;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import run.fleek.application.profile.ProfileInfoTypeHolder;
import run.fleek.domain.certification.CertificationResource;
import run.fleek.domain.certification.UserCertification;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.ProfileService;
import run.fleek.domain.profile.image.ProfileImage;
import run.fleek.domain.profile.image.ProfileImageService;
import run.fleek.domain.profile.info.ProfileInfoService;
import run.fleek.domain.users.FleekUser;
import run.fleek.enums.Certification;
import run.fleek.enums.ImageType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FaceCertificationHandler implements CertificationHandler {

  private final ProfileService profileService;
  private final ProfileImageService profileImageService;

  private final ProfileInfoService profileInfoService;
  private final ProfileInfoTypeHolder profileInfoTypeHolder;

  @Override
  public Certification getCertification() {
    return Certification.FACE;
  }

  @Override
  public void handle(UserCertification userCertification, List<CertificationResource> resources) {
    FleekUser fleekUser = userCertification.getFleekUser();

    List<Profile> profiles = profileService.listProfiles(fleekUser);
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
}
