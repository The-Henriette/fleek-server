package run.fleek.domain.fruitman.tracking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.application.fruitman.order.dto.OrderPageDto;
import run.fleek.domain.fruitman.deal.Deal;
import run.fleek.domain.fruitman.user.FruitManUser;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDealService {
  private final UserDealRepository userDealRepository;

  @Transactional(readOnly = true)
  public Optional<UserDeal> getSampleParticipant(Deal deal) {
    return userDealRepository.findFirstByDeal(deal);
  }

  @Transactional(readOnly = true)
  public List<UserDeal> listUserDeal(Deal deal) {
    return userDealRepository.findAllByDeal(deal);
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
}
