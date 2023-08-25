package run.fleek.common.client.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoAuthTokenDto {
  @JsonProperty("token_type")
  private String tokenType;
  @JsonProperty("access_token")
  private String accessToken;

  @JsonProperty("id_token")
  private String idToken;
  @JsonProperty("expires_in")
  private int expiresIn;
  @JsonProperty("refresh_token")
  private String refreshToken;
  @JsonProperty("refresh_token_expires_in")
  private int refreshTokenExpiresIn;
  private String scope;
}
