package run.fleek.application.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequestDto {
  private String accessToken;
  private String refreshToken;
}
