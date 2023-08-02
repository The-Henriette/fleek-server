package run.fleek.domain.profile.image.dto;

import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

import static run.fleek.common.constants.Constants.CDN_PREFIX;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileImageDto {
  private String profileName;
  private String chatProfileKey;
  private List<String> profileImageUrls;

  public List<String> getProfileImageUrls() {
    return profileImageUrls.stream()
      .map(url -> CDN_PREFIX + url)
      .collect(Collectors.toList());
  }
}
