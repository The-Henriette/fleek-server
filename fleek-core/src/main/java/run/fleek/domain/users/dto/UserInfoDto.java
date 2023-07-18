package run.fleek.domain.users.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
  private Long userId;
  private String userName;
  private String countryCode;
  private String languageCode;

  // TODO: Some more infos...
}
