package run.fleek.domain.users.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignInDto {
  private String userName;
  private String password;
}
