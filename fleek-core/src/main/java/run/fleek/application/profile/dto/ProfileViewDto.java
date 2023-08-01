package run.fleek.application.profile.dto;

import lombok.*;
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
  private String gender;
  private String orientation;
  private String bio;
  private List<String> profileImages;
  private List<CertificationDto> certifications;
  private List<ProfileCategoryInfoDto> details;

  public List<String> getProfileImages() {
    return profileImages.stream()
      .map(url -> CDN_PREFIX + url)
      .collect(Collectors.toList());
  }
}
