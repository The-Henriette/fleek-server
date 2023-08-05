package run.fleek.application.exchange.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeWatchDto {
  private Boolean success;
  private List<String> faceUrls;
  private String failureReason;
}
