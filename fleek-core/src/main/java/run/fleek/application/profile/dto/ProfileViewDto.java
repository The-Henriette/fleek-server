package run.fleek.application.profile.dto;

import lombok.*;
import run.fleek.application.profile.vo.ProfileEditDto;
import run.fleek.domain.certification.dto.CertificationDto;
import run.fleek.domain.profile.dto.ProfileCategoryInfoDto;

import java.util.List;
import java.util.stream.Collectors;

import static run.fleek.common.constants.Constants.CDN_PREFIX;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileViewDto {
  private Long profileId;
  private String profileName;
  private Long age;
  private Long dateOfBirth;
  private String gender;
  private String orientation;
  private String bio;
  private String job;
  private String occupation;
  private List<String> profileImages;
  private List<String> profileImagePaths;
  private List<CertificationDto> certifications;
  private List<ProfileCategoryInfoDto> details;

  public List<String> getProfileImages() {
    return profileImages.stream()
      .map(url -> CDN_PREFIX + url)
      .collect(Collectors.toList());
  }

  public List<String> listProfileImage() {
    return profileImages;
  }

  public ProfileEditDto to() {
    return ProfileEditDto.builder()
      .profileName(profileName)
      .bio(bio)
      .profileImages(getProfileImagePaths())
      .details(details.stream().flatMap(category -> category.getInfos().stream()).collect(Collectors.toList()))
      .build();
  }
}
