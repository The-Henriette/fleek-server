package run.fleek.application.exchange.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeResponseDto {
  private Boolean success;
  private Long exchangeId;
  private String validationCode;
  private String validationMessage;
}
