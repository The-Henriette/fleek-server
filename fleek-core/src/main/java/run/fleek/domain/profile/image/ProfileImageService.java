package run.fleek.domain.profile.image;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import run.fleek.domain.profile.Profile;
import run.fleek.domain.profile.image.dto.ProfileImageDto;
import run.fleek.enums.ImageType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ProfileImageService {

  private final ProfileImageRepository profileImageRepository;

  @Transactional(readOnly = true)
  public List<ProfileImage> listProfileImageByProfileId(Long profileId) {
    return profileImageRepository.findAllByProfile_ProfileId(profileId);
  }

  @Transactional(readOnly = true)
  public List<ProfileImageDto> listProfileImagesByUserId(Long fleekUserId) {
    List<ProfileImage> profileImages = profileImageRepository.listProfileImageByFleekUserId(fleekUserId);

    Map<String /* profileName*/, List<ProfileImage>> profileToImagesMap = profileImages.stream()
      .collect(Collectors.groupingBy(pi -> pi.getProfile().getProfileName()));

    return profileToImagesMap.entrySet().stream()
      .map(e -> ProfileImageDto.builder()
        .profileName(e.getKey())
        .profileImageUrls(e.getValue().stream()
          .map(ProfileImage::getImageUrl)
          .collect(Collectors.toList()))
        .build())
      .collect(Collectors.toList());
  }

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
