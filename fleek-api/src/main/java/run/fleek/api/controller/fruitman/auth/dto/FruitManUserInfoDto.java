package run.fleek.api.controller.fruitman.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FruitManUserInfoDto {
  private String nickName;
  private String email;
  private String profileUrl;
}
