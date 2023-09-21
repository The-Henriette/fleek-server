package run.fleek.application.fruitman.order.dto;

import lombok.*;
import run.fleek.application.fruitman.order.vo.OrderVo;

import static run.fleek.common.constants.Constants.CDN_PREFIX;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
  private String orderId;
  private Long dealId;
  private String dealName;
  private String dealImage;
  private Integer purchasePrice;
  private String dealTrackingStatus;
  private String dealTrackingStatusDescription;
  private Long orderedAt;
  private Long paymentDue;
  private Long refundDue;
  private Long pdd;

  public String getDealImage() {
    return CDN_PREFIX + dealImage;
  }

  public static OrderDto from(OrderVo vo) {
    return OrderDto.builder()
      .orderId(vo.getOrderId())
      .dealId(vo.getDealId())
      .dealName(vo.getDealName())
      .dealImage(vo.getDealImage())
      .purchasePrice(vo.getPurchasePrice())
      .dealTrackingStatus(vo.getDealTrackingStatus().name())
      .dealTrackingStatusDescription(vo.getDealTrackingStatus().getDescription())
      .orderedAt(vo.getOrderedAt())
      .paymentDue(vo.getPaymentDue())
      .refundDue(vo.getRefundDue())
      .pdd(vo.getPdd())
      .build();
  }
}
