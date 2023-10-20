package run.fleek.application.fruitman.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.fruitman.order.dto.*;
import run.fleek.common.client.toss.TossWebclient;
import run.fleek.common.client.toss.dto.TossPaymentRequestDto;
import run.fleek.common.client.toss.dto.TossPaymentResponseDto;
import run.fleek.common.exception.FleekException;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.domain.fruitman.deal.*;
import run.fleek.domain.fruitman.tracking.*;
import run.fleek.domain.fruitman.user.FruitManUser;
import run.fleek.domain.fruitman.user.FruitManUserService;
import run.fleek.domain.fruitman.user.UserRefundInfo;
import run.fleek.domain.fruitman.user.UserRefundInfoService;
import run.fleek.enums.*;
import run.fleek.utils.JsonUtil;
import run.fleek.utils.RandomUtil;
import run.fleek.utils.TimeUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static run.fleek.common.constants.Constants.FruitMan.DEFAULT_REFUND_TIME;
import static run.fleek.enums.DealTrackingStatus.PAID_STATES;
import static run.fleek.enums.DealTrackingStatus.REFUNDABLE_STATES;

@Component
@RequiredArgsConstructor
public class OrderApplication {

  private static final int ORDER_AMOUNT = 1;
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
  private final CartService cartService;
  private final TossWebclient tossWebclient;
  private final DealConstraintService dealConstraintService;

  private String generateOrderId() {
    return String.format("%s_%s",
      TimeUtil.convertEpochToDateString(TimeUtil.getCurrentTimeMillisUtc()),
      RandomUtil.generateRandomString());
  }

  private UserDeal addUserDeal(Cart cart, Long dealId) {
    Deal deal = dealService.getDeal(dealId);
    Long orderedAt = TimeUtil.getCurrentTimeMillisUtc();
    Long pdd = TimeUtil.toStartOfNextDaySeoul(new Date(deal.getExpiredAt())).getTime() - 1;

    UserDeal userDeal = UserDeal.builder()
      .fruitManUser(cart.getFruitManUser())
      .deal(deal)
      .orderId(cart.getOrderId())
      .orderedAt(orderedAt)
      .paidAt(null)
      .trackingStatus(DealTrackingStatus.PENDING)
      .pdd(pdd)
      .purchaseOption(cart.getPurchaseOption())
      .cart(cart)
      .build();
    userDealService.addUserDeal(userDeal);
    return userDeal;
  }

  // 미세한 함수 로직 변경(Input과 Output은 같음). 이유는 이후 ORDER_AMOUNT가 1이 아닌 경우에도 대응 가능하도록 하기 위함.
  // Todo: 추후 AMOUNT 칼럼이 USER_DEAL에 추가될 경우, 더 이상 size()로 해결해서는 안됨.
  private boolean isInvalidDeal(FruitManUser fruitmanUser, Deal deal) {
    DealConstraint dealConstraint = dealConstraintService.getDealConstraint(deal);
    Integer quantityPerUser = dealConstraint.getQuantityPerUser();
    if (quantityPerUser > 0){
      return userDealService.listPaidTeamDeal(fruitmanUser, deal).size() + ORDER_AMOUNT > dealConstraint.getQuantityPerUser();
    }
    return false;
  }
  @Transactional
  public OrderDto addOrder(OrderAddDto orderAddDto) {
    Cart cart = cartService.getCart(orderAddDto.getCartId());
    UserDeal userDeal = userDealService.getUserDeals(cart).get(0);

    Deal deal = userDeal.getDeal();
    FruitManUser fruitManUser = userDeal.getFruitManUser();

    if (isInvalidDeal(fruitManUser, deal) &&
      userDeal.getPurchaseOption().equals(PurchaseOption.TEAM)){
      throw new FleekException("해당 상품은 중복해서 구매할 수 없습니다.");
    }

    Map<PurchaseOption, DealPurchaseOption> dealPurchaseOptionMap = dealPurchaseOptionService.listDealPurchaseOption(userDeal.getDeal()).stream()
      .collect(Collectors.toMap(DealPurchaseOption::getPurchaseOption, dealPurchaseOption -> dealPurchaseOption));

    DealTrackingStatus status = userDeal.getTrackingStatus();
    Long orderedAt = TimeUtil.getCurrentTimeMillisUtc();
    userDeal.setOrderedAt(orderedAt);

    userDealService.addUserDeal(userDeal);

    UserDealTracking userDealTracking = UserDealTracking.builder()
      .userDeal(userDeal)
      .trackingStatus(status)
      .trackingAt(orderedAt)
      .build();
    userDealTrackingService.addUserDealTracking(userDealTracking);

    UserDeliveryDetail userDeliveryDetail = UserDeliveryDetail.builder()
      .userDeal(userDeal)
      .recipientName(orderAddDto.getRecipientName())
      .recipientPhoneNumber(orderAddDto.getRecipientPhoneNumber())
      .postalCode(orderAddDto.getPostalCode())
      .mainAddress(orderAddDto.getMainAddress())
      .subAddress(orderAddDto.getSubAddress())
      .deliveryMemo(orderAddDto.getDeliveryMemo())
      .build();
    userDeliveryDetailService.addUserDeliveryDetail(userDeliveryDetail);

    UserPayment userPayment = UserPayment.builder()
      .userDeal(userDeal)
      .paymentMethod(PaymentMethod.valueOf(orderAddDto.getPaymentMethod()))
      .paymentDue(orderedAt + 3600000)
      .amount(dealPurchaseOptionMap.get(cart.getPurchaseOption()).getPrice())
      .build();
    userPaymentService.addUserPayment(userPayment);

    if (Objects.nonNull(orderAddDto.getReceiptInfo())) {
      UserPaymentReceipt userReceiptInfo = UserPaymentReceipt.builder()
        .userPayment(userPayment)
        .receiptPurpose(ReceiptPurpose.valueOf(orderAddDto.getReceiptInfo().getReceiptPurpose()))
        .receiptTargetType(Optional.ofNullable(orderAddDto.getReceiptInfo()).map(r -> ReceiptTargetType.valueOf(r.getReceiptTargetType())).orElse(null))
        .receiptTarget(orderAddDto.getReceiptInfo().getReceiptTarget())
        .build();
      userPaymentReceiptService.addUserPaymentReceipt(userReceiptInfo);
    }

    return OrderDto.builder()
      .orderId(cart.getOrderId())
      .dealId(userDeal.getDeal().getDealId())
      .dealName(userDeal.getDeal().getDealName())
      .purchasePrice(dealPurchaseOptionMap.get(cart.getPurchaseOption()).getPrice())
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
  public UserDeal cancelOrder(String orderId) {
    UserDeal userDeal = userDealService.getUserDeal(orderId);
    DealTrackingStatus originalStatus = userDeal.getTrackingStatus();
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

    if (PAID_STATES.contains(originalStatus)) {
      UserPayment userPayment = userPaymentService.getUserPayment(userDeal);
      userPayment.setRefundDue(cancelledAt + DEFAULT_REFUND_TIME);
      userPayment.setRefundAmount(userPayment.getAmount());

      if (userPayment.getPaymentMethod().equals(PaymentMethod.TOSS)) {
        tossWebclient.cancelPayment(userPayment);
      }
      userPaymentService.addUserPayment(userPayment);

      if (originalStatus.equals(DealTrackingStatus.TEAM_PURCHASE_PENDING)) {
        DealConstraint constraint = dealConstraintService.getDealConstraint(userDeal.getDeal());
        constraint.setCurrentQuantity(Math.max(constraint.getCurrentQuantity() - 1, 0));
        dealConstraintService.addDealConstraint(constraint);
      }
    }

    userDealService.addUserDeal(userDeal);
    return userDeal;
  }

  @Transactional
  public CartDto addCart(CartAddDto cartAddDto) {
    Long userId = fleekUserContext.getUserId();
    FruitManUser fruitManUser = fruitManUserService.getFruitManUser(userId);

    List<Deal> deals = dealService.listDealByIds(cartAddDto.getDealIds());
    if (deals.stream().anyMatch(d -> d.getDealStatus().equals(DealStatus.SUCCESS)) &&
      cartAddDto.getPurchaseOption().equals(PurchaseOption.TEAM.name())) {
      throw new FleekException("품절된 상품이 포함되어 있습니다.");
    }

    if (deals.stream().anyMatch(deal -> isInvalidDeal(fruitManUser, deal)) &&
      cartAddDto.getPurchaseOption().equals(PurchaseOption.TEAM.name())){
      throw new FleekException("해당 상품은 중복해서 구매할 수 없습니다.");
    }

    Cart cart = Cart.builder()
      .cartType(CartType.valueOf(cartAddDto.getCartType()))
      .purchaseOption(PurchaseOption.valueOf(cartAddDto.getPurchaseOption()))
      .fruitManUser(fruitManUser)
      .orderId(this.generateOrderId())
      .build();
    Integer purchasePrice = dealPurchaseOptionService.listDealPurchaseOption(cartAddDto.getDealIds()).stream()
      .filter(dpo -> dpo.getPurchaseOption().equals(cart.getPurchaseOption()))
      .map(DealPurchaseOption::getPrice)
      .reduce(0, Integer::sum);

    Integer deliveryPrice = dealService.listDealByIds(cartAddDto.getDealIds()).stream()
      .map(Deal::getDeliveryPrice)
      .reduce(0, Integer::sum);

    cart.setPurchasePrice(purchasePrice);
    cart.setDeliveryPrice(deliveryPrice);

    cartService.addCart(cart);

    List<UserDeal> dealList = cartAddDto.getDealIds().stream()
      .map(dealId -> this.addUserDeal(cart, dealId))
      .collect(Collectors.toList());

    return CartDto.builder()
      .cartId(cart.getCartId())
      .purchaseOption(cart.getPurchaseOption().name())
      .cartType(cart.getCartType().name())
      .orderId(cart.getOrderId())
      .dealIds(dealList.stream()
        .map(ud -> ud.getDeal().getDealId())
        .collect(Collectors.toList()))
      .purchasePrice(cart.getPurchasePrice())
      .deliveryPrice(cart.getDeliveryPrice())
      .build();
  }

  @Transactional(readOnly = true)
  public CartDto getCart(Long cartId) {
    Cart cart = cartService.getCart(cartId);
    List<UserDeal> userDealList = userDealService.getUserDeals(cart);

    return CartDto.builder()
      .cartId(cart.getCartId())
      .purchaseOption(cart.getPurchaseOption().name())
      .cartType(cart.getCartType().name())
      .orderId(cart.getOrderId())
      .dealIds(userDealList.stream()
        .map(ud -> ud.getDeal().getDealId())
        .collect(Collectors.toList()))
      .purchasePrice(cart.getPurchasePrice())
      .deliveryPrice(cart.getDeliveryPrice())
      .build();

  }

  @Transactional
  public PaymentResponseDto updatePaymentStatus(String orderId, PaymentRequestDto dto) {
    Cart cart = cartService.getCartByOrderId(orderId);
    List<UserDeal> userDealList = userDealService.getUserDeals(cart);

    TossPaymentRequestDto requestDto = TossPaymentRequestDto.from(dto, orderId);
    TossPaymentResponseDto tossPaymentResponseDto = tossWebclient.requestPayment(requestDto);
    if (!tossPaymentResponseDto.getSuccess()) {
      return PaymentResponseDto.builder()
        .success(false)
        .failureCode(tossPaymentResponseDto.getErrorCode())
        .failureReason(tossPaymentResponseDto.getErrorMessage())
        .build();
    }

    cart.setPaymentDetail(JsonUtil.write(tossPaymentResponseDto));
    cartService.addCart(cart);

    AtomicInteger remainingCount = new AtomicInteger();

    userDealList.forEach(userDeal -> {
      DealTrackingStatus status;
      if (userDeal.getPurchaseOption().equals(PurchaseOption.TEAM)) {
        status = DealTrackingStatus.TEAM_PURCHASE_PENDING;
      } else {
        status = DealTrackingStatus.PAID;
      }

      userDeal.setTrackingStatus(status);
      userDeal.setPaidAt(TimeUtil.getCurrentTimeMillisUtc());
      userDealService.addUserDeal(userDeal);

      UserPayment userPayment = userPaymentService.getUserPayment(userDeal);
      userPayment.setTossPaymentKey(tossPaymentResponseDto.getPaymentKey());
      userPaymentService.addUserPayment(userPayment);

      UserDealTracking userDealTracking = UserDealTracking.builder()
        .userDeal(userDeal)
        .trackingAt(TimeUtil.getCurrentTimeMillisUtc())
        .trackingStatus(status)
        .build();
      userDealTrackingService.addUserDealTracking(userDealTracking);

      Deal deal = userDeal.getDeal();
      DealConstraint constraint = dealConstraintService.getDealConstraint(deal);
      if (userDeal.getPurchaseOption().equals(PurchaseOption.TEAM)) {
        constraint.setCurrentQuantity(constraint.getCurrentQuantity() + 1);
        dealConstraintService.addDealConstraint(constraint);
      }

      remainingCount.addAndGet(constraint.getRequiredQuantity() - constraint.getCurrentQuantity());
    });

    return PaymentResponseDto.builder()
      .orderId(orderId)
      .success(true)
      .rawData(tossPaymentResponseDto)
      .remainingCount(remainingCount.longValue())
      .build();
  }

}
