package run.fleek.application.auth.dto;

import lombok.*;
import run.fleek.configuration.auth.dto.TokenDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpResultDto {
  private String chatUri;
  private String chatProfileKey;
  private String grantType;
  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpiresAt;
  private Long refreshTokenExpiresAt;

  public void setToken(TokenDto token) {
    this.grantType = token.getGrantType();
    this.accessToken = token.getAccessToken();
    this.refreshToken = token.getRefreshToken();
    this.accessTokenExpiresAt = token.getAccessTokenExpiresAt();
    this.refreshTokenExpiresAt = token.getRefreshTokenExpiresAt();
  }
}
