package run.fleek.common.client.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoIdTokenDto {
  @JsonProperty("aud")
  private String appKey;

  @JsonProperty("sub")
  private String userId;

  @JsonProperty("auth_time")
  private Long authTime;

  @JsonProperty("iss")
  private String issuer;

  @JsonProperty("exp")
  private Long expiration;

  @JsonProperty("iat")
  private Long issuedAt;

  private String nickname;

  private String picture;

  private String email;

}

