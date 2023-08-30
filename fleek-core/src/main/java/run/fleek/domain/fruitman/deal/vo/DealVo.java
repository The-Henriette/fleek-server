package run.fleek.domain.fruitman.deal.vo;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.MappingProjection;
import lombok.*;
import run.fleek.domain.fruitman.deal.QDeal;
import run.fleek.domain.fruitman.deal.QDealConstraint;
import run.fleek.domain.fruitman.delivery.QDeliveryAreaGroup;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DealVo {
  private Long dealId;
  private String dealName;
  private Integer originalPrice;
  private Integer salesPrice;
  private String deliveryMethodString;
  private String deliveryAreaGroupName;
  private Integer requiredQuantity;
  private Integer currentQuantity;
  private String dealThumbnail;
  private Long effectedAt;
  private Long expiredAt;

  public static final DealVoProjection DEAL_VO_PROJECTION = new DealVoProjection();

  public static class DealVoProjection extends MappingProjection<DealVo> {

    public DealVoProjection() {
      super(DealVo.class,
        QDeal.deal.dealId,
        QDeal.deal.dealName,
        QDeal.deal.marketPrice,
        QDeal.deal.salesPrice,
        QDeal.deal.deliveryMethods,
        QDeliveryAreaGroup.deliveryAreaGroup.deliveryAreaGroupName,
        QDealConstraint.dealConstraint.requiredQuantity,
        QDealConstraint.dealConstraint.currentQuantity,
        QDeal.deal.dealThumbnail,
        QDeal.deal.effectedAt,
        QDeal.deal.expiredAt
        );
    }

    @Override
    protected DealVo map(Tuple row) {
      return DealVo.builder()
        .dealId(row.get(QDeal.deal.dealId))
        .dealName(row.get(QDeal.deal.dealName))
        .originalPrice(row.get(QDeal.deal.marketPrice))
        .salesPrice(row.get(QDeal.deal.salesPrice))
        .deliveryMethodString(row.get(QDeal.deal.deliveryMethods))
        .deliveryAreaGroupName(row.get(QDeliveryAreaGroup.deliveryAreaGroup.deliveryAreaGroupName))
        .requiredQuantity(row.get(QDealConstraint.dealConstraint.requiredQuantity))
        .currentQuantity(row.get(QDealConstraint.dealConstraint.currentQuantity))
        .dealThumbnail(row.get(QDeal.deal.dealThumbnail))
        .effectedAt(row.get(QDeal.deal.effectedAt))
        .expiredAt(row.get(QDeal.deal.expiredAt))
        .build();
    }
  }
}
