package run.fleek.api.controller.wallet.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWalletDto {
  private Long amount;
}
