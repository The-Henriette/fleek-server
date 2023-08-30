package run.fleek.application.fruitman.order;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.fruitman.order.dto.OrderAddDto;
import run.fleek.application.fruitman.order.dto.OrderDetailDto;
import run.fleek.application.fruitman.order.dto.OrderDto;
import run.fleek.application.fruitman.order.dto.OrderPageDto;
import run.fleek.common.exception.FleekException;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.domain.fruitman.deal.Deal;
import run.fleek.domain.fruitman.deal.DealPurchaseOption;
import run.fleek.domain.fruitman.deal.DealPurchaseOptionService;
import run.fleek.domain.fruitman.deal.DealService;
import run.fleek.domain.fruitman.tracking.*;
import run.fleek.domain.fruitman.user.FruitManUser;
import run.fleek.domain.fruitman.user.FruitManUserService;
import run.fleek.domain.fruitman.user.UserRefundInfo;
import run.fleek.domain.fruitman.user.UserRefundInfoService;
import run.fleek.enums.*;
import run.fleek.utils.RandomUtil;
import run.fleek.utils.TimeUtil;

import java.sql.Time;
import java.util.*;
import java.util.stream.Collectors;

import static run.fleek.common.constants.Constants.FruitMan.DEFAULT_REFUND_TIME;
import static run.fleek.enums.DealTrackingStatus.PAID_STATES;
import static run.fleek.enums.DealTrackingStatus.REFUNDABLE_STATES;

@Component
@RequiredArgsConstructor
public class OrderApplication {

  private final DealService dealService;
  private final FleekUserContext fleekUserContext;
  private final FruitManUserService fruitManUserService;
  private final UserDealService userDealService;
  private final UserDealTrackingService userDealTrackingService;
  private final DealPurchaseOptionService dealPurchaseOptionService;
  private final UserPaymentService userPaymentService;
  private final UserPaymentReceiptService userPaymentReceiptService;
  private final UserDeliveryDetailService userDeliveryDetailService;
  private final UserRefundInfoService userRefundInfoService;

  @Transactional
  public OrderDto addOrder(OrderAddDto orderAddDto) {
    Deal deal = dealService.getDeal(orderAddDto.getDealId());
    Map<PurchaseOption, DealPurchaseOption> dealPurchaseOptionMap = dealPurchaseOptionService.listDealPurchaseOption(deal).stream()
      .collect(Collectors.toMap(DealPurchaseOption::getPurchaseOption, dealPurchaseOption -> dealPurchaseOption));

    Long userId = fleekUserContext.getUserId();
    FruitManUser fruitManUser = fruitManUserService.getFruitManUser(userId);
    DealTrackingStatus status = DealTrackingStatus.PENDING; // TODO : Toss Payment 연동 시 변경
    String orderId = String.format("%s_%s", TimeUtil.convertEpochToDateString(TimeUtil.getCurrentTimeMillisUtc()), RandomUtil.generateRandomString());
    Long pdd = TimeUtil.toStartOfNextDaySeoul(new Date(deal.getExpiredAt())).getTime() - 1;

    Long orderedAt = TimeUtil.getCurrentTimeMillisUtc();

    UserDeal userDeal = UserDeal.builder()
      .fruitManUser(fruitManUser)
      .deal(deal)
      .orderId(orderId)
      .orderedAt(orderedAt)
      .paidAt(null)
      .trackingStatus(status)
      .pdd(pdd)
      .purchaseOption(PurchaseOption.valueOf(orderAddDto.getPurchaseOption()))
      .build();
    userDealService.addUserDeal(userDeal);

    UserDealTracking userDealTracking = UserDealTracking.builder()
      .userDeal(userDeal)
      .trackingStatus(status)
      .trackingAt(orderedAt)
      .build();
    userDealTrackingService.addUserDealTracking(userDealTracking);

    UserDeliveryDetail userDeliveryDetail = UserDeliveryDetail.builder()
      .recipientName(orderAddDto.getRecipientName())
      .recipientPhoneNumber(orderAddDto.getRecipientPhoneNumber())
      .postalCode(orderAddDto.getPostalCode())
      .mainAddress(orderAddDto.getMainAddress())
      .subAddress(orderAddDto.getSubAddress())
      .build();
    userDeliveryDetailService.addUserDeliveryDetail(userDeliveryDetail);

    UserPayment userPayment = UserPayment.builder()
      .userDeal(userDeal)
      .paymentMethod(PaymentMethod.valueOf(orderAddDto.getPaymentMethod()))
      .paymentDue(orderedAt + 3600000)
      .amount(dealPurchaseOptionMap.get(PurchaseOption.valueOf(orderAddDto.getPurchaseOption())).getPrice())
      .build();
    userPaymentService.addUserPayment(userPayment);

    if (Objects.nonNull(orderAddDto.getReceiptInfo())) {
      UserPaymentReceipt userReceiptInfo = UserPaymentReceipt.builder()
        .userPayment(userPayment)
        .receiptPurpose(ReceiptPurpose.valueOf(orderAddDto.getReceiptInfo().getReceiptPurpose()))
        .receiptTargetType(ReceiptTargetType.valueOf(orderAddDto.getReceiptInfo().getReceiptTargetType()))
        .receiptTarget(orderAddDto.getReceiptInfo().getReceiptTarget())
        .build();
      userPaymentReceiptService.addUserPaymentReceipt(userReceiptInfo);
    }

    return OrderDto.builder()
      .orderId(orderId)
      .dealId(deal.getDealId())
      .dealName(deal.getDealName())
      .purchasePrice(dealPurchaseOptionMap.get(PurchaseOption.valueOf(orderAddDto.getPurchaseOption())).getPrice())
      .dealTrackingStatus(status.getName())
      .dealTrackingStatusDescription(status.getDescription())
      .build();
  }

  public OrderPageDto listOrders(int size, int page) {
    Long userId = fleekUserContext.getUserId();
    FruitManUser fruitManUser = fruitManUserService.getFruitManUser(userId);

    return userDealService.pageOrders(fruitManUser, size, page);
  }

  public OrderDetailDto getOrder(String orderId) {
    UserDeal userDeal = userDealService.getUserDeal(orderId);
    Deal deal = userDeal.getDeal();

    OrderDetailDto detail = new OrderDetailDto();
    detail.setUserDealInfo(userDeal);
    detail.setDealInfo(deal);

    UserPayment userPayment = userPaymentService.getUserPayment(userDeal);
    detail.setUserPaymentInfo(userPayment);

    UserDeliveryDetail userDeliveryDetail = userDeliveryDetailService.getUserDeliveryDetail(userDeal);
    detail.setUserDeliveryDetailInfo(userDeliveryDetail);

    Optional<UserRefundInfo> refundInfoOpt = userRefundInfoService.getUserRefundInfo(userDeal.getFruitManUser());
    refundInfoOpt.ifPresent(detail::setUserRefundInfo);

    return detail;
  }

  @Transactional
  public void cancelOrder(String orderId) {
    UserDeal userDeal = userDealService.getUserDeal(orderId);
    if (!REFUNDABLE_STATES.contains(userDeal.getTrackingStatus())) {
      throw new FleekException("주문 취소가 불가능한 상태입니다.");
    }

    DealTrackingStatus status = DealTrackingStatus.CANCELLED;
    userDeal.setTrackingStatus(status);

    long cancelledAt = TimeUtil.getCurrentTimeMillisUtc();

    UserDealTracking userDealTracking = UserDealTracking.builder()
      .userDeal(userDeal)
      .trackingAt(cancelledAt)
      .trackingStatus(status)
      .build();
    userDealTrackingService.addUserDealTracking(userDealTracking);

    if (PAID_STATES.contains(userDeal.getTrackingStatus())) {
      UserPayment userPayment = userPaymentService.getUserPayment(userDeal);
      userPayment.setRefundDue(cancelledAt + DEFAULT_REFUND_TIME);
      userPayment.setRefundAmount(userPayment.getAmount());
      userPaymentService.addUserPayment(userPayment);
    }

    userDealService.addUserDeal(userDeal);
  }
}
