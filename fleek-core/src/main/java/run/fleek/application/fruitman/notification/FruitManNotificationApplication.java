package run.fleek.application.fruitman.notification;

import com.google.api.client.util.Lists;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.common.client.nhn.NhnWebClient;
import run.fleek.common.client.nhn.dto.NhnMessageRequestDto;
import run.fleek.domain.fruitman.deal.Deal;
import run.fleek.domain.fruitman.deal.DealConstraint;
import run.fleek.domain.fruitman.deal.DealConstraintService;
import run.fleek.domain.fruitman.deal.DealService;
import run.fleek.domain.fruitman.tracking.*;
import run.fleek.enums.DealStatus;
import run.fleek.enums.DealTrackingStatus;
import run.fleek.enums.PurchaseOption;
import run.fleek.utils.FleekStringUtils;
import run.fleek.utils.TimeUtil;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FruitManNotificationApplication {

  private final NhnWebClient nhnWebClient;
  private final UserDealService userDealService;
  private final DealConstraintService dealConstraintService;
  private final UserDeliveryDetailService userDeliveryDetailService;
  private final UserDealTrackingService userDealTrackingService;
  private final DealService dealService;

  @Async
  public void sendNotificationOnTeamPurchasePending(String orderId) {
    UserDeal userDeal = userDealService.getUserDeal(orderId);
    Deal deal
      = userDeal.getDeal();
    DealConstraint constraint = dealConstraintService.getDealConstraint(deal);
    UserDeliveryDetail detail = userDeliveryDetailService.getUserDeliveryDetail(userDeal);


    if (userDeal.getPurchaseOption().equals(PurchaseOption.TEAM)) {
      nhnWebClient.sendNhnMessage("team_standby", Collections.singletonList(
        NhnMessageRequestDto.NhnRecipientDto.builder()
          .recipientNo(detail.getRecipientPhoneNumber())
          .templateParameter(
            Map.of(
              "상품명", FleekStringUtils.truncateString(deal.getDealName(), 11) + "...",
              "잔여갯수", String.valueOf(Math.max(constraint.getRequiredQuantity() - constraint.getCurrentQuantity(), 0))
            )
          )
          .build()
      ));
    } else {
      nhnWebClient.sendNhnMessage("solo_complete", Collections.singletonList(
        NhnMessageRequestDto.NhnRecipientDto.builder()
          .recipientNo(detail.getRecipientPhoneNumber())
          .templateParameter(
            Map.of(
              "상품명", FleekStringUtils.truncateString(deal.getDealName(), 11) + "..."
            )
          )
          .build()
      ));
    }
  }

  public void sendNotificationOnCancel(UserDeal userDeal) {
    UserDeliveryDetail detail = userDeliveryDetailService.getUserDeliveryDetail(userDeal);

    nhnWebClient.sendNhnMessage("cancelled", Collections.singletonList(
      NhnMessageRequestDto.NhnRecipientDto.builder()
        .recipientNo(detail.getRecipientPhoneNumber())
        .templateParameter(
          Map.of(
            "상품명", FleekStringUtils.truncateString(userDeal.getDeal().getDealName(), 11) + "..."
          )
        )
        .build()
    ));
  }

  @Async
  @Transactional
  public void sendNotificationOnTeamPurchaseSuccess(String orderId) {
    UserDeal userDeal = userDealService.getUserDeal(orderId);

    Deal deal = userDeal.getDeal();

    if (deal.getDealStatus().equals(DealStatus.SUCCESS)) {
      return;
    }

    List<UserDeal> successTargetDeals = userDealService.listUserDeal(deal).stream()
      .filter(ud -> ud.getTrackingStatus().equals(DealTrackingStatus.TEAM_PURCHASE_PENDING) &&
        ud.getPurchaseOption().equals(PurchaseOption.TEAM))
      .collect(Collectors.toList());

    List<UserDeliveryDetail> details = userDeliveryDetailService.listUserDeliveryDetailBy(successTargetDeals);

    List<NhnMessageRequestDto.NhnRecipientDto> recipientDtoList = details.stream()
      .map(UserDeliveryDetail::getRecipientPhoneNumber)
      .map(ph -> NhnMessageRequestDto.NhnRecipientDto.builder()
        .recipientNo(ph)
        .templateParameter(Map.of("상품명", FleekStringUtils.truncateString(userDeal.getDeal().getDealName(), 11) + "..."))
        .build())
      .collect(Collectors.toList());

    nhnWebClient.sendNhnMessage("team_success", recipientDtoList);

    deal.setDealStatus(DealStatus.SUCCESS);
    dealService.addDeal(deal);

    List<UserDeal> putUserDealList = Lists.newArrayList();
    List<UserDealTracking> userDealTrackingList = Lists.newArrayList();
    successTargetDeals.forEach(ud -> {
      if (ud.getPurchaseOption().equals(PurchaseOption.TEAM)) {
        ud.setTrackingStatus(DealTrackingStatus.TEAM_PURCHASE_SUCCESS);
        putUserDealList.add(ud);
        userDealTrackingList.add(UserDealTracking.builder()
          .userDeal(ud)
          .trackingAt(TimeUtil.getCurrentTimeMillisUtc())
          .trackingStatus(DealTrackingStatus.TEAM_PURCHASE_SUCCESS)
          .build()
        );
      }
    });

    userDealService.putUserDealList(putUserDealList);
    userDealTrackingService.putUserDealTrackingList(userDealTrackingList);
  }
}
