package run.fleek.api.controller.fruitman.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;
import run.fleek.api.controller.fruitman.auth.dto.FruitManUserInfoDto;
import run.fleek.application.fruitman.auth.FruitManAuthApplication;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.domain.fruitman.user.FruitManUser;
import run.fleek.domain.fruitman.user.FruitManUserService;

@RestController
@RequiredArgsConstructor
public class FruitManAuthController {

  private final FruitManAuthApplication fruitManAuthApplication;
  private final FleekUserContext fleekUserContext;
  private final FruitManUserService fruitManUserService;

  @GetMapping("/fruitman/me")
  public FruitManUserInfoDto getMyInfo() {

    Long userId = fleekUserContext.getUserId();
    FruitManUser fruitManUser = fruitManUserService.getFruitManUser(userId);

    return FruitManUserInfoDto.builder()
      .email(fruitManUser.getEmail())
      .nickName(fruitManUser.getNickname())
      .profileUrl(fruitManUser.getProfileUrl())
      .build();
  }

  @GetMapping("/fruitman/login/{providerCode}")
  public ResponseEntity<String> requestProviderAccess(@PathVariable String providerCode) {
    return fruitManAuthApplication.requestProviderAccess(providerCode);
  }

  @GetMapping("/fruitman/provider/redirect")
  public ResponseEntity<String> processRedirect(@RequestParam(required = false) String code, @RequestParam(required = false) String state) {
    if (!StringUtils.hasLength(code) || !StringUtils.hasLength(state)) {

      return ResponseEntity.status(302)
        .header("Location", "https://fruitman.pro/error")
        .build();
    }

    TokenDto token = fruitManAuthApplication.processRedirect(code, state);

    return ResponseEntity.status(302)
      .header("Location", String.format("https://fruitman.pro/login/success?accessToken=%s&refreshToken=%s&accessTokenExpiry=%s",
        token.getAccessToken(),
        token.getRefreshToken(),
        token.getAccessTokenExpiresAt()
      ))
      .build();

  }
}
