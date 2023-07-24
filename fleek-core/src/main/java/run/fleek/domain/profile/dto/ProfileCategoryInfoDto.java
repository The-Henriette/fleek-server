package run.fleek.domain.profile.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCategoryInfoDto {
  private String categoryCode;
  private String categoryDescription;
  private List<ProfileInfoDto> infos;
}
