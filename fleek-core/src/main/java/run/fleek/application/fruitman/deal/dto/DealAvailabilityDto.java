package run.fleek.application.fruitman.deal.dto;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealAvailabilityDto {
  private Boolean available;
  private String deliveryAreaGroupName;
}
