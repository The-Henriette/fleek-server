package run.fleek.application.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationDto {
  private String verificationCode;
  private String verificationNumber;
}
