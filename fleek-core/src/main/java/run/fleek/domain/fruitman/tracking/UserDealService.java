package run.fleek.domain.fruitman.tracking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.fruitman.order.dto.OrderPageDto;
import run.fleek.domain.fruitman.deal.Cart;
import run.fleek.domain.fruitman.deal.Deal;
import run.fleek.domain.fruitman.user.FruitManUser;
import run.fleek.enums.PurchaseOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static run.fleek.enums.DealTrackingStatus.COUNTABLE_STATES;

@Service
@RequiredArgsConstructor
public class UserDealService {
  private final UserDealRepository userDealRepository;

  @Transactional(readOnly = true)
  public Optional<UserDeal> getSampleParticipant(Deal deal) {
    return userDealRepository.findFirstByDealAndTrackingStatusInAndPurchaseOption(deal, new ArrayList<>(COUNTABLE_STATES), PurchaseOption.TEAM);
  }

  @Transactional(readOnly = true)
  public List<UserDeal> listUserDealByDeal(Deal deal) {
    return userDealRepository.findAllByDeal(deal);
  }

  // 결제가 정상적으로 된 팀 구매 주문만 컬렉션에 담아서 반환
  @Transactional(readOnly = true)
  public List<UserDeal> listPaidTeamDeal(FruitManUser fruitmanUser, Deal deal) {
    return userDealRepository.findAllByFruitManUserAndDealAndTrackingStatusInAndPurchaseOption(fruitmanUser, deal, new ArrayList<>(COUNTABLE_STATES), PurchaseOption.TEAM);
  }


  @Transactional
  public void addUserDeal(UserDeal userDeal) {
    userDealRepository.save(userDeal);
  }

  @Transactional(readOnly = true)
  public OrderPageDto pageOrders(FruitManUser user, int size, int page) {
    return userDealRepository.pageOrders(user, size, page);
  }

  @Transactional(readOnly = true)
  public UserDeal getUserDeal(String orderId) {
    return userDealRepository.findByOrderId(orderId).orElseThrow(() -> new IllegalArgumentException("해당 주문이 존재하지 않습니다."));
  }

  @Transactional(readOnly = true)
  public List<UserDeal> getUserDeals(Cart cart) {
    return userDealRepository.findAllByCart(cart);
  }

  @Transactional
  public void putUserDealList(List<UserDeal> putUserDealList) {
    if (putUserDealList.isEmpty()) {
      return;
    }

    userDealRepository.saveAll(putUserDealList);
  }
}

