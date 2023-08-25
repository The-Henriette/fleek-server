package run.fleek.api.controller.fruitman.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;
import run.fleek.application.fruitman.auth.FruitManAuthApplication;
import run.fleek.configuration.auth.dto.TokenDto;

@RestController
@RequiredArgsConstructor
public class FruitManAuthController {

  private final FruitManAuthApplication fruitManAuthApplication;

  @GetMapping("/fruitman/login/{providerCode}")
  public ResponseEntity<String> requestProviderAccess(@PathVariable String providerCode) {
    return fruitManAuthApplication.requestProviderAccess(providerCode);
  }

  @GetMapping("/fruitman/provider/redirect")
  public ResponseEntity<String> processRedirect(@RequestParam(required = false) String code, @RequestParam(required = false) String state) {
    if (!StringUtils.hasLength(code) || !StringUtils.hasLength(state)) {

      return ResponseEntity.status(302)
        .header("Location", "fruitman.pro/error")
        .build();
    }

    TokenDto token = fruitManAuthApplication.processRedirect(code, state);

    return ResponseEntity.status(302)
      .header("Location", String.format("fruitman.pro/login/success?accessToken=%s&refreshToken=%s&accessTokenExpiry=%s",
        token.getAccessToken(),
        token.getRefreshToken(),
        token.getAccessTokenExpiresAt()
      ))
      .build();

  }
}
