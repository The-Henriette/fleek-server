package run.fleek.application.fruitman.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import run.fleek.application.fruitman.provider.FruitManAuthProvider;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.enums.ProviderCode;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FruitManAuthApplication {

  private final List<FruitManAuthProvider> authProviders;

  private FruitManAuthProvider resolveAuthProvider(String providerCode) {
    return authProviders.stream()
      .filter(authProvider -> authProvider.isSupport(ProviderCode.valueOf(providerCode)))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Not supported provider code: " + providerCode));
  }

  public ResponseEntity<String> requestProviderAccess(String providerCode) {
    FruitManAuthProvider authProvider = resolveAuthProvider(providerCode);
    return authProvider.requestAccess();
  }

  public TokenDto processRedirect(String code, String state) {
    FruitManAuthProvider authProvider = resolveAuthProvider(state);
    return authProvider.processRedirect(code);
  }
}
