package run.fleek.application.fruitman.order.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
  private String paymentKey;
  private Integer amount;
}
