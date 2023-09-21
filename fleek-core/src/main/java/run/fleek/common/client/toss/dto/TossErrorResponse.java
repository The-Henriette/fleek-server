package run.fleek.common.client.toss.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossErrorResponse {
  private String code;
  private String message;
}
