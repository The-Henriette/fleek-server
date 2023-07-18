package run.fleek.domain.users.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {

  private String name;
  private String loginId;
  private String profilePicture;
  private Integer followers;
  private Integer following;

}
