package run.fleek.application.fruitman.order.dto;

import lombok.*;
import run.fleek.domain.fruitman.deal.Deal;
import run.fleek.domain.fruitman.tracking.UserDeal;
import run.fleek.domain.fruitman.tracking.UserDeliveryDetail;
import run.fleek.domain.fruitman.tracking.UserPayment;
import run.fleek.domain.fruitman.user.UserRefundInfo;

import static run.fleek.common.constants.Constants.CDN_PREFIX;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
  private String orderId;
  private String dealTrackingStatus;
  private String dealTrackingStatusDescription;
  private Long orderedAt;
  private Long paidAt;

  private Long dealId;
  private String dealName;
  private String dealImage;
  private String paymentMethod;
  private Integer purchasePrice;
  private Integer deliveryPrice;

  private String recipientName;
  private String recipientPhoneNumber;
  private String postalCode;
  private String mainAddress;
  private String subAddress;

  private String refundAccountName;
  private String refundBankName;
  private String refundAccountNumber;
  private Integer refundAmount;

  private Long updatedAt;

  public String getDealImage() {
    return CDN_PREFIX + dealImage;
  }

  public void setUserDealInfo(UserDeal userDeal) {
    this.orderId = userDeal.getOrderId();
    this.dealTrackingStatus = userDeal.getTrackingStatus().getName();
    this.dealTrackingStatusDescription = userDeal.getTrackingStatus().getDescription();
    this.orderedAt = userDeal.getOrderedAt();
    this.paidAt = userDeal.getPaidAt();

    this.updatedAt = userDeal.getUpdatedAt();
  }

  public void setDealInfo(Deal deal) {
    this.dealId = deal.getDealId();
    this.dealName = deal.getDealName();
    this.deliveryPrice = deal.getDeliveryPrice();
    this.dealImage = deal.getDealThumbnail();
  }

  public void setUserPaymentInfo(UserPayment userPayment) {
    this.paymentMethod = userPayment.getPaymentMethod().getDescription();
    this.purchasePrice = userPayment.getAmount();
  }

  public void setUserDeliveryDetailInfo(UserDeliveryDetail userDeliveryDetail) {
    this.recipientName = userDeliveryDetail.getRecipientName();
    this.recipientPhoneNumber = userDeliveryDetail.getRecipientPhoneNumber();
    this.postalCode = userDeliveryDetail.getPostalCode();
    this.mainAddress = userDeliveryDetail.getMainAddress();
    this.subAddress = userDeliveryDetail.getSubAddress();
  }

  public void setUserRefundInfo(UserRefundInfo userRefundInfo) {
    this.refundAccountName = userRefundInfo.getRefundAccountName();
    this.refundBankName = userRefundInfo.getRefundBankName();
    this.refundAccountNumber = userRefundInfo.getRefundAccountNumber();
  }
}
