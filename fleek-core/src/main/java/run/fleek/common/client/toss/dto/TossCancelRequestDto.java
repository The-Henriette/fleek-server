package run.fleek.common.client.toss.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TossCancelRequestDto {
  private String cancelReason;
}
