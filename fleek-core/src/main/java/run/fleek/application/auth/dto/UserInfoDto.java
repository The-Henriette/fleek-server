package run.fleek.application.auth.dto;

import lombok.*;
import run.fleek.domain.profile.image.dto.ProfileImageDto;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
  private List<ProfileImageDto> profiles;
  private String phoneNumber;
  private int age;
  private String gender;
  private String orientation;
}
