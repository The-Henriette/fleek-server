package run.fleek.application.fruitman.order.vo;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.MappingProjection;
import lombok.*;
import org.aspectj.weaver.ast.Or;
import run.fleek.domain.fruitman.deal.QDeal;
import run.fleek.domain.fruitman.tracking.QUserDeal;
import run.fleek.domain.fruitman.tracking.QUserPayment;
import run.fleek.enums.DealTrackingStatus;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderVo {
  private String orderId;
  private Long dealId;
  private String dealName;
  private String dealImage;
  private Integer purchasePrice;
  private DealTrackingStatus dealTrackingStatus;
  private Long orderedAt;
  private Long paymentDue;
  private Long refundDue;
  private Long pdd;

  public static OrderVoProjection ORDER_VO_PROJECTION = new OrderVoProjection();
  public static class OrderVoProjection extends MappingProjection<OrderVo> {

    public OrderVoProjection() {
      super(OrderVo.class,
        QUserDeal.userDeal.orderId,
        QDeal.deal.dealId,
        QDeal.deal.dealName,
        QDeal.deal.dealThumbnail,
        QUserPayment.userPayment.amount,
        QUserDeal.userDeal.trackingStatus,
        QUserDeal.userDeal.orderedAt,
        QUserPayment.userPayment.paymentDue,
        QUserPayment.userPayment.refundDue,
        QUserDeal.userDeal.pdd
        );
    }

    @Override
    protected OrderVo map(Tuple row) {
      return OrderVo.builder()
        .orderId(row.get(QUserDeal.userDeal.orderId))
        .dealId(row.get(QDeal.deal.dealId))
        .dealName(row.get(QDeal.deal.dealName))
        .dealImage(row.get(QDeal.deal.dealThumbnail))
        .purchasePrice(row.get(QUserPayment.userPayment.amount))
        .dealTrackingStatus(row.get(QUserDeal.userDeal.trackingStatus))
        .orderedAt(row.get(QUserDeal.userDeal.orderedAt))
        .paymentDue(row.get(QUserPayment.userPayment.paymentDue))
        .refundDue(row.get(QUserPayment.userPayment.refundDue))
        .pdd(row.get(QUserDeal.userDeal.pdd))
        .build();
    }
  }
}
