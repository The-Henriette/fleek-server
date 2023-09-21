package run.fleek.application.fruitman.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import run.fleek.common.client.toss.dto.TossPaymentResponseDto;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto {
  private TossPaymentResponseDto rawData;
  private String orderId;
  private Boolean success;
  private String failureReason;
  private String failureCode;

  @JsonIgnore
  private Long remainingCount;
}
