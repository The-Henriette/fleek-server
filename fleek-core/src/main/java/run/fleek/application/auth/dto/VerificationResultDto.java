package run.fleek.application.auth.dto;

import lombok.*;
import run.fleek.configuration.auth.dto.TokenDto;

import java.util.Optional;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationResultDto {
  private Boolean success;
  private String grantType;
  private String accessToken;
  private String refreshToken;
  private Long accessTokenExpiresAt;
  private Long refreshTokenExpiresAt;


  public static VerificationResultDto success(TokenDto tokenDto) {
    return VerificationResultDto.builder()
      .success(true)
      .grantType(Optional.ofNullable(tokenDto).map(TokenDto::getGrantType).orElse(null))
      .accessToken(Optional.ofNullable(tokenDto).map(TokenDto::getAccessToken).orElse(null))
      .refreshToken(Optional.ofNullable(tokenDto).map(TokenDto::getRefreshToken).orElse(null))
      .accessTokenExpiresAt(Optional.ofNullable(tokenDto).map(TokenDto::getAccessTokenExpiresAt).orElse(null))
      .refreshTokenExpiresAt(Optional.ofNullable(tokenDto).map(TokenDto::getRefreshTokenExpiresAt).orElse(null))
      .build();
  }
}
