package run.fleek.application.fruitman.deal.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealPageDto {
  private Integer page;
  private Long totalSize;
  private List<DealDto> deals;
}
