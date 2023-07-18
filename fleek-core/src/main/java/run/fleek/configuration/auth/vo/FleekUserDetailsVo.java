package run.fleek.configuration.auth.vo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FleekUserDetailsVo {
  private String languageCode;
  private String countryCode;
}
