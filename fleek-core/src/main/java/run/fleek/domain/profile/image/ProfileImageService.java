package run.fleek.domain.profile.image;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import run.fleek.domain.profile.Profile;
import run.fleek.enums.ImageType;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProfileImageService {

  private final ProfileImageRepository profileImageRepository;

  @Transactional
  public void addProfileImages(Profile profile, List<String> imageUrls) {
    if (CollectionUtils.isEmpty(imageUrls)) {
      return;
    }

    List<ProfileImage> profileImages = Lists.newArrayList();
    for (int i = 0; i < imageUrls.size(); i++) {
      profileImages.add(ProfileImage.builder()
          .profile(profile)
          .imageType(ImageType.PROFILE_POST)
          .imageUrl(imageUrls.get(i))
          .orderNumber(i)
          .build());
    }

    profileImageRepository.saveAll(profileImages);
  }
}
