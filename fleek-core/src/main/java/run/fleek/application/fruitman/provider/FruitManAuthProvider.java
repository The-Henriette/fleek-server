package run.fleek.application.fruitman.provider;

import org.springframework.http.ResponseEntity;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.enums.ProviderCode;

public interface FruitManAuthProvider {
  boolean isSupport(ProviderCode providerCode);
  ResponseEntity<String> requestAccess();
  TokenDto processRedirect(String accessCode);
}
