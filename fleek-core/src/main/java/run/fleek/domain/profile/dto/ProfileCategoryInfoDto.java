package run.fleek.domain.profile.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import run.fleek.enums.ProfileInfoCategory;

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
  @JsonIgnore
  private ProfileInfoCategory category;
}
