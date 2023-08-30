package run.fleek.application.fruitman.deal.dto;

import lombok.*;
import run.fleek.domain.fruitman.deal.Deal;
import run.fleek.domain.fruitman.deal.DealConstraint;
import run.fleek.domain.fruitman.deal.DealPurchaseOption;
import run.fleek.utils.JsonUtil;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealDetailDto {
  private Long dealId;
  private String dealName;
  private Integer originalPrice;
  private Integer salesPrice;
  private Integer discountRate;
  private List<PurchaseOptionDetailDto> purchaseOptions;
  private String dealThumbnail;
  private List<String> imageList;
  private Long effectedAt;
  private Long expiredAt;
  private String participant;
  private Integer requiredQuantity;
  private Integer currentQuantity;
  private Integer remainingQuantity;

  public void setDealInfo(Deal deal) {
    this.dealId = deal.getDealId();
    this.dealName = deal.getDealName();
    this.originalPrice = deal.getMarketPrice();
    this.salesPrice = deal.getSalesPrice();
    this.discountRate = (int) ((1 - (double) deal.getSalesPrice() / deal.getMarketPrice()) * 100);
    this.dealThumbnail = deal.getDealThumbnail();
    this.imageList = JsonUtil.readList(deal.getDealImages(), String.class);
    this.effectedAt = deal.getEffectedAt();
    this.expiredAt = deal.getExpiredAt();
  }

  public void setPurchaseOptionsInfo(List<DealPurchaseOption> purchaseOptions) {
    this.purchaseOptions = purchaseOptions.stream()
      .map(PurchaseOptionDetailDto::from)
      .collect(Collectors.toList());
  }

  public void setConstraintInfo(DealConstraint constraint) {
    this.requiredQuantity = constraint.getRequiredQuantity();
    this.currentQuantity = constraint.getCurrentQuantity();
    this.remainingQuantity = constraint.getRequiredQuantity() - constraint.getCurrentQuantity();
  }
}
