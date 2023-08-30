package run.fleek.application.fruitman.deal.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOptionDto {
  private String purchaseTypeCode;
  private Integer price;
}
