package run.fleek.domain.fruitman.tracking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.domain.fruitman.deal.Deal;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDeliveryDetailService {
  private final UserDeliveryDetailRepository userDeliveryDetailRepository;

  @Transactional
  public void addUserDeliveryDetail(UserDeliveryDetail userDeliveryDetail) {
    userDeliveryDetailRepository.save(userDeliveryDetail);
  }

  @Transactional(readOnly = true)
  public UserDeliveryDetail getUserDeliveryDetail(UserDeal userDeal) {
    return userDeliveryDetailRepository.findByUserDeal(userDeal);
  }

  public List<UserDeliveryDetail> getUserDeliveryDetailsByDeal(Deal Deal) {
    return userDeliveryDetailRepository.findAllByUserDeal_Deal(Deal);
  }
}
