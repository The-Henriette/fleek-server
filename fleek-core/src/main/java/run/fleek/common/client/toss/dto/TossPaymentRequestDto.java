package run.fleek.common.client.toss.dto;

import lombok.*;
import run.fleek.application.fruitman.order.dto.PaymentRequestDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossPaymentRequestDto {
  private String paymentKey;
  private Integer amount;
  private String orderId;

  public static TossPaymentRequestDto from(PaymentRequestDto paymentRequestDto, String orderId) {
    return TossPaymentRequestDto.builder()
        .paymentKey(paymentRequestDto.getPaymentKey())
        .amount(paymentRequestDto.getAmount())
        .orderId(orderId)
        .build();
  }
}
