package run.fleek.application.exchange.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeRequestDto {
  private String chatUrl;
  private String requesterProfileName;
}
