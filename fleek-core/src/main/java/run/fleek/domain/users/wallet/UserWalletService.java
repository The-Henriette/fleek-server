package run.fleek.domain.users.wallet;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UserWalletService {

  private final UserWalletRepository userWalletRepository;

  @Transactional(readOnly = true)
  public UserWallet getWallet(Long userId) {
    return userWalletRepository.findByFleekUser_FleekUserId(userId)
        .orElseThrow(() -> new IllegalArgumentException("지갑이 존재하지 않습니다."));
  }

  @Transactional
  public void putWallet(UserWallet userWallet) {
    userWalletRepository.save(userWallet);
  }
}
