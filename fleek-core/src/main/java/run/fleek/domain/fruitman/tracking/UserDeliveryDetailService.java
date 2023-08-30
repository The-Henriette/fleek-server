package run.fleek.domain.fruitman.tracking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
