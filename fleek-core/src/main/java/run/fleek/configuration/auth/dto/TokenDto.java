package run.fleek.configuration.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDto {
  private String grantType;
  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpiresAt;
  private Long refreshTokenExpiresAt;
}
