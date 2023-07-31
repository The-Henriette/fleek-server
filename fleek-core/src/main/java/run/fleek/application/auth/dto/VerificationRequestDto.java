package run.fleek.application.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VerificationRequestDto {

  private String phoneNumber;
}
