package run.fleek.application.fruitman.deal.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealParticipantsDto {
  private Long dealId;
  private Long effectedAt;
  private Long expiredAt;
  private Integer requiredQuantity;
  private Integer currentQuantity;
  private Integer remainingQuantity;

  private List<String> participants;
}
