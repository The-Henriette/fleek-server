package run.fleek.domain.profile.image.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImageDto {
  private String profileName;
  private List<String> profileImageUrls;
}
