package run.fleek.application.profile.dto;

import lombok.*;
import run.fleek.domain.certification.dto.CertificationDto;
import run.fleek.domain.profile.dto.ProfileCategoryInfoDto;

import java.util.List;

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
}
