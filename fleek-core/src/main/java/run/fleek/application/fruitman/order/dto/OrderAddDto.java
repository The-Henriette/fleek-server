package run.fleek.application.fruitman.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAddDto {
  private Long cartId;
  private String recipientName;
  private String recipientPhoneNumber;
  private String postalCode;
  private String mainAddress;
  private String subAddress;
  private String paymentMethod;
  private ReceiptInfoDto receiptInfo;
}
