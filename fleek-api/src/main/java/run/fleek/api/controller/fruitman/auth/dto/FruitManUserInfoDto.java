package run.fleek.api.controller.fruitman.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FruitManUserInfoDto {
  private Long userId;
  private String nickName;
  private String email;
  private String profileUrl;
  private String refundAccountName;
  private String refundBankName;
  private String refundAccountNumber;

}
