package run.fleek.api.controller.fruitman.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.result.view.RedirectView;
import run.fleek.api.controller.fruitman.auth.dto.FruitManUserInfoDto;
import run.fleek.application.auth.dto.RefreshRequestDto;
import run.fleek.application.fruitman.auth.FruitManAuthApplication;
import run.fleek.configuration.auth.FleekTokenProvider;
import run.fleek.configuration.auth.FleekUserContext;
import run.fleek.configuration.auth.dto.TokenDto;
import run.fleek.domain.fruitman.user.FruitManUser;
import run.fleek.domain.fruitman.user.FruitManUserService;
import run.fleek.domain.fruitman.user.UserRefundInfo;
import run.fleek.domain.fruitman.user.UserRefundInfoService;

@RestController
@RequiredArgsConstructor
public class FruitManAuthController {

  private final FruitManAuthApplication fruitManAuthApplication;
  private final FleekUserContext fleekUserContext;
  private final FruitManUserService fruitManUserService;
  private final UserRefundInfoService userRefundInfoService;
  private final FleekTokenProvider fleekTokenProvider;

  @GetMapping("/fruitman/me")
  public FruitManUserInfoDto getMyInfo() {

    Long userId = fleekUserContext.getUserId();
    FruitManUser fruitManUser = fruitManUserService.getFruitManUser(userId);

    UserRefundInfo refundInfo = userRefundInfoService.getUserRefundInfo(fruitManUser)
      .orElse(UserRefundInfo.builder().build());

    return FruitManUserInfoDto.builder()
      .email(fruitManUser.getEmail())
      .nickName(fruitManUser.getNickname())
      .profileUrl(fruitManUser.getProfileUrl())
      .refundAccountName(refundInfo.getRefundAccountName())
      .refundBankName(refundInfo.getRefundBankName())
      .refundAccountNumber(refundInfo.getRefundAccountNumber())
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

  @PostMapping("/fruitman/auth/refresh")
  public TokenDto refresh(@RequestBody RefreshRequestDto refreshRequestDto) {
    Long id = fleekTokenProvider.getId(refreshRequestDto.getAccessToken());
    return fleekTokenProvider.generateTokenDto(FruitManUser.builder()
        .fruitManUserId(id)
      .build());
  }

}
