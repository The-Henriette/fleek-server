package run.fleek.domain.fruitman.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import run.fleek.enums.ProviderCode;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FruitManUserService {

  private final FruitManUserRepository fruitManUserRepository;

  @Transactional(readOnly = true)
  public Optional<FruitManUser> getFruitManUserByProviderCodeAndProviderUserId(ProviderCode providerCode, String providerUserId) {
    return fruitManUserRepository.findByProviderCodeAndProviderId(providerCode, providerUserId);
  }

  @Transactional
  public void addUser(FruitManUser fruitManUser) {
    fruitManUserRepository.save(fruitManUser);
  }
}
