package run.fleek.application.exchange.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendbirdExchangeDataDto {
  private String state;
  private String exchangeId;
}
