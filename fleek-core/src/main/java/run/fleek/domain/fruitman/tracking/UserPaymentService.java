package run.fleek.domain.fruitman.tracking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPaymentService {

  private final UserPaymentRepository userPaymentRepository;

  @Transactional
  public void addUserPayment(UserPayment userPayment) {
    userPaymentRepository.save(userPayment);
  }

  public UserPayment getUserPayment(UserDeal userDeal) {
    return userPaymentRepository.findByUserDeal(userDeal);
  }
}
