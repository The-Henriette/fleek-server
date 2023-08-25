package run.fleek.application.fruitman.deal.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealDto {

  private String dealName;
  private Integer originalPrice;
  private Integer salesPrice;
  private Integer discountRate;
  private List<String> deliveryMethods;
  private List<String> deliveryAreas;
  private Integer requiredQuantity;
  private Integer currentQuantity;
  private Integer remainingQuantity;
  private String dealThumbnail;
}
