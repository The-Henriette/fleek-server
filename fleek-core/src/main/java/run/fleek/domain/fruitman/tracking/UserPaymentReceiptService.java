package run.fleek.domain.fruitman.tracking;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserPaymentReceiptService {
  private final UserPaymentReceiptRepository userPaymentReceiptRepository;

  @Transactional
  public void addUserPaymentReceipt(UserPaymentReceipt userReceiptInfo) {
    userPaymentReceiptRepository.save(userReceiptInfo);
  }
}
