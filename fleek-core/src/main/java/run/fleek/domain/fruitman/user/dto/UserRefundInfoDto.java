package run.fleek.domain.fruitman.user.dto;

import lombok.*;
import run.fleek.domain.fruitman.user.UserRefundInfo;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRefundInfoDto {
  private String refundAccountName;
  private String refundBankName;
  private String refundAccountNumber;

  public static UserRefundInfoDto from(UserRefundInfo userRefundInfo) {
    return UserRefundInfoDto.builder()
      .refundAccountName(userRefundInfo.getRefundAccountName())
      .refundBankName(userRefundInfo.getRefundBankName())
      .refundAccountNumber(userRefundInfo.getRefundAccountNumber())
      .build();
  }
}
