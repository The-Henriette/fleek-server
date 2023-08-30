package run.fleek.application.fruitman.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptInfoDto {
  private String receiptPurpose;
  private String receiptTargetType;
  private String receiptTarget;
}
