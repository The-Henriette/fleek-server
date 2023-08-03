package run.fleek.application.chat.dto;

import lombok.*;

import java.util.Objects;

import static run.fleek.common.constants.Constants.CDN_PREFIX;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatCounterpartDto {
  private Boolean faceCertified;
  private String profileUrl;
  private String profileName;

  public String getProfileUrl() {
    if (Objects.isNull(profileUrl)) {
      return null;
    }

    return CDN_PREFIX + profileUrl;
  }
}
