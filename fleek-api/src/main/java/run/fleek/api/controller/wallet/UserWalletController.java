package run.fleek.api.controller.wallet;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import run.fleek.api.controller.wallet.dto.UserWalletDto;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.domain.users.wallet.UserWallet;
import run.fleek.domain.users.wallet.UserWalletService;

@RestController
@RequiredArgsConstructor
public class UserWalletController {

  private final UserWalletService userWalletService;
  private final FleekUserContext fleekUserContext;

  @GetMapping("/wallet")
  public UserWalletDto getWallet() {
    UserWallet wallet = userWalletService.getWallet(fleekUserContext.getUserId());
    return UserWalletDto.builder()
        .amount(wallet.getAmount())
        .build();
  }

  @PostMapping("/wallet/purchase")
  public void purchase(@RequestParam Long increment) {
    UserWallet wallet = userWalletService.getWallet(fleekUserContext.getUserId());
    userWalletService.purchase(wallet, increment);
  }
}
