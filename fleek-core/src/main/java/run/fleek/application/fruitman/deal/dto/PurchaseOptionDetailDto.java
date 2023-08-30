package run.fleek.application.fruitman.deal.dto;

import lombok.*;
import run.fleek.domain.fruitman.deal.DealPurchaseOption;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOptionDetailDto {
  private Integer order;
  private String name;
  private String purchaseOptionCode;
  private String requirements;
  private String description;
  private Integer price;

  public static PurchaseOptionDetailDto from(DealPurchaseOption dealPurchaseOption) {
    return PurchaseOptionDetailDto.builder()
      .order(dealPurchaseOption.getPurchaseOption().getOrder())
      .name(dealPurchaseOption.getPurchaseOption().getName())
      .purchaseOptionCode(dealPurchaseOption.getPurchaseOption().name())
      .requirements(dealPurchaseOption.getPurchaseOption().getRequirements())
      .description(dealPurchaseOption.getPurchaseOption().getDescription())
      .price(dealPurchaseOption.getPrice())
      .build();
  }
}
