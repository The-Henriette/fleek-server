package run.fleek.application.fruitman.deal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.fruitman.deal.dto.*;
import run.fleek.domain.fruitman.deal.*;
import run.fleek.domain.fruitman.deal.vo.DealVo;
import run.fleek.domain.fruitman.delivery.DeliveryAreaGroup;
import run.fleek.domain.fruitman.delivery.DeliveryAreaGroupService;
import run.fleek.domain.fruitman.sku.Sku;
import run.fleek.domain.fruitman.sku.SkuService;
import run.fleek.domain.fruitman.tracking.UserDeal;
import run.fleek.domain.fruitman.tracking.UserDealService;
import run.fleek.domain.fruitman.user.FruitManUser;
import run.fleek.enums.PurchaseOption;
import run.fleek.utils.MaskingUtil;
import run.fleek.utils.TimeUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static run.fleek.common.constants.Constants.FruitMan.DEFAULT_DEAL_START_TIME;
import static run.fleek.enums.DealTrackingStatus.COUNTABLE_STATES;

@Component
@RequiredArgsConstructor
public class DealApplication {

  private final DealService dealService;
  private final DealSkuService dealSkuService;
  private final SkuService skuService;
  private final DeliveryAreaGroupService deliveryAreaGroupService;
  private final DealConstraintService dealConstraintService;
  private final DealPurchaseOptionService dealPurchaseOptionService;
  private final UserDealService userDealService;

  @Transactional
  public void addDeal(DealAddDto dealAddDto) {
    // 1. fetch dealSkus and deliveryAreaGroup
    Map<Long, Sku> skuMap = skuService.listSku(dealAddDto.getDealSkus().stream()
      .map(DealSkuDto::getSkuId)
      .collect(Collectors.toList()))
      .stream().collect(Collectors.toMap(Sku::getSkuId, sku -> sku));

    DeliveryAreaGroup deliveryAreaGroup = deliveryAreaGroupService.getDeliveryAreaGroup(dealAddDto.getDeliveryGroupId());
    // 2. create deal
    // 2-1. if deal thumbnail is empty, then use dealSku's first image and its images
    Deal deal = Deal.from(dealAddDto, skuMap, deliveryAreaGroup);
    Date targetDate = dealAddDto.getTargetDate();

    deal.setEffectedAt(TimeUtil.toStartOfDaySeoul(targetDate).getTime() + DEFAULT_DEAL_START_TIME);
    deal.setExpiredAt(TimeUtil.toStartOfNextDaySeoul(targetDate).getTime());

    dealService.addDeal(deal);

    // 3. create dealSkus
    List<DealSku> dealSkuList = dealAddDto.getDealSkus().stream()
      .map(dto -> DealSku.from(dto, deal, skuMap.get(dto.getSkuId())))
      .collect(Collectors.toList());
    dealSkuService.addDealSkus(dealSkuList);

    // 4. create deal constraint
    dealConstraintService.addDealConstraint(DealConstraint.from(dealAddDto, deal));

    // 5. create deal purchase options
    List<DealPurchaseOption> options = dealAddDto.getPurchaseOptions().stream()
      .map(dto -> DealPurchaseOption.from(deal, dto))
      .collect(Collectors.toList());
    dealPurchaseOptionService.addDealPurchaseOptions(options);
  }

  public List<DealDto> listTodayDeal() {
    Long todayBeginning = TimeUtil.toStartOfDaySeoul(new Date()).getTime();
    Long tomorrowBeginning = TimeUtil.toStartOfNextDaySeoul(new Date()).getTime();

    List<DealVo> dealVoList = dealService.listDeal(todayBeginning, tomorrowBeginning);
    return dealVoList.stream()
      .map(DealDto::from)
      .collect(Collectors.toList());
  }

  public DealDetailDto getDealDetail(Long dealId) {
    DealDetailDto detail = new DealDetailDto();

    Deal deal = dealService.getDeal(dealId);
    detail.setDealInfo(deal);

    List<DealPurchaseOption> purchaseOptions = dealPurchaseOptionService.listDealPurchaseOption(deal);
    detail.setPurchaseOptionsInfo(purchaseOptions);

    DealConstraint constraint = dealConstraintService.getDealConstraint(deal);
    detail.setConstraintInfo(constraint);

    Optional<UserDeal> sampleParticipant = userDealService.getSampleParticipant(deal);
    String participant = sampleParticipant.map(d -> d.getFruitManUser().getEmail()).orElse(null);
    detail.setParticipant(participant);

    return detail;
  }

  public DealParticipantsDto getDealParticipants(Long dealId) {
    Deal deal = dealService.getDeal(dealId);
    DealConstraint constraint = dealConstraintService.getDealConstraint(deal);
    List<UserDeal> participants = userDealService.listUserDealByDeal(deal).stream()
      .filter(d -> COUNTABLE_STATES.contains(d.getTrackingStatus()))
      .filter(d -> d.getPurchaseOption().equals(PurchaseOption.TEAM))
      .collect(Collectors.toList());
    return DealParticipantsDto.builder()
      .dealId(dealId)
      .effectedAt(deal.getEffectedAt())
      .expiredAt(deal.getExpiredAt())
      .participants(participants.stream()
        .map(UserDeal::getFruitManUser)
        .map(FruitManUser::getNickname)
        .map(MaskingUtil::name)
        .collect(Collectors.toList()))
      .requiredQuantity(constraint.getRequiredQuantity())
      .currentQuantity(constraint.getCurrentQuantity())
      .remainingQuantity(constraint.getRequiredQuantity() - constraint.getCurrentQuantity())
      .build();
  }

  public DealAvailabilityDto getDealAvailability(Long dealId, String postalCode) {
    Deal deal = dealService.getDeal(dealId);
    DeliveryAreaGroup deliveryAreaGroup = deal.getDeliveryAreaGroup();

    boolean isAvailable = deliveryAreaGroupService.isAvailable(deliveryAreaGroup, postalCode);
    return DealAvailabilityDto.builder()
      .available(isAvailable)
      .deliveryAreaGroupName(deliveryAreaGroup.getDeliveryAreaGroupName())
      .build();
  }

  public DealPageDto listPreviousDeals(int size, int page) {
    Long todayBeginning = TimeUtil.toStartOfDaySeoul(new Date()).getTime();
    return dealService.pageDealsBefore(todayBeginning, size, page);
  }
}
